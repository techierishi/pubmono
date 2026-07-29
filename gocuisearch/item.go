package gocuisearch

import (
	"math"
	"strconv"
	"strings"
	"time"
	"unicode"

	"golang.org/x/exp/utf8string"
)

const itemLocationLength = 30
const dots = "…"

type Item struct {
	isRaw bool

	time float64

	sameGitRepo bool
	exitCode    int

	ContentWithColor string
	Content          string
	ContentOut       string

	Idx int
}

type ItemColumns struct {
	DateWithColor string
	Date          string

	ContentWithColor string
	Content          string

	Key string
}

func splitStatusLineToLines(statusLine string, printedLineLength, realLineLength int) []string {
	var statusLineSlice []string
	// status line
	var idxSt, idxEnd int
	var nextLine bool
	tab := "    "
	tabSize := len(tab)
	for idxSt < len(statusLine) {
		idxEnd = idxSt + printedLineLength
		if nextLine {
			idxEnd -= tabSize
		}

		if idxEnd > len(statusLine) {
			idxEnd = len(statusLine)
		}
		str := statusLine[idxSt:idxEnd]

		indent := " "
		if nextLine {
			indent += tab
		}
		statusLineSlice = append(statusLineSlice, highlightStatus(rightCutPadString(indent+str, realLineLength))+"\n")
		idxSt += printedLineLength
		nextLine = true
	}
	return statusLineSlice
}

// DrawStatusLine ...
func (i Item) DrawStatusLine(compactRendering bool, printedLineLength, realLineLength int) []string {
	if i.isRaw {
		return splitStatusLineToLines(i.Content, printedLineLength, realLineLength)
	}
	secs := int64(i.time)
	nsecs := int64((i.time - float64(secs)) * 1e9)
	tm := time.Unix(secs, nsecs)
	const timeFormat = "2006-01-02 15:04:05"
	timeString := tm.Format(timeFormat)

	separator := "    "
	stLine := timeString + separator + ":" + separator + i.Content
	return splitStatusLineToLines(stLine, printedLineLength, realLineLength)
}

// GetEmptyStatusLine .
func GetEmptyStatusLine(printedLineLength, realLineLength int) []string {
	return splitStatusLineToLines("- no result selected -", printedLineLength, realLineLength)
}

// DrawItemColumns ...
func (i Item) DrawItemColumns(compactRendering bool, debug bool) ItemColumns {
	if i.isRaw {
		notAvailable := "n/a"
		return ItemColumns{
			Date:             notAvailable + " ",
			DateWithColor:    notAvailable + " ",
			Content:          i.Content,
			ContentWithColor: i.ContentWithColor,
			Key:              strconv.Itoa(i.Idx),
		}
	}

	return ItemColumns{
		Content:          i.Content,
		ContentWithColor: i.ContentWithColor,
		Key:              strconv.Itoa(i.Idx),
	}
}

func minInt(values ...int) int {
	min := math.MaxInt32
	for _, val := range values {
		if val < min {
			min = val
		}
	}
	return min
}

func (ic ItemColumns) ProduceLine(dateLength int, flagsLength int, header bool, showDate bool, debug bool) (string, int, error) {
	var err error
	line := ""
	spacer := "  "
	if flagsLength > 5 || header {
		spacer = " "
	}
	line += spacer + ic.ContentWithColor

	length := dateLength + flagsLength + len(spacer) + len(ic.Content)
	return line, length, err
}

func rightCutLeftPadString(str string, newLen int) string {
	if newLen <= 0 {
		return ""
	}
	utf8Str := utf8string.NewString(str)
	strLen := utf8Str.RuneCount()
	if newLen > strLen {
		return strings.Repeat(" ", newLen-strLen) + str
	} else if newLen < strLen {
		return utf8Str.Slice(0, newLen-1) + dots
	}
	return str
}

func leftCutPadString(str string, newLen int) string {
	if newLen <= 0 {
		return ""
	}
	utf8Str := utf8string.NewString(str)
	strLen := utf8Str.RuneCount()
	if newLen > strLen {
		return strings.Repeat(" ", newLen-strLen) + str
	} else if newLen < strLen {
		return dots + utf8string.NewString(str).Slice(strLen-newLen+1, strLen)
	}
	return str
}

func rightCutPadString(str string, newLen int) string {
	if newLen <= 0 {
		return ""
	}
	utf8Str := utf8string.NewString(str)
	strLen := utf8Str.RuneCount()
	if newLen > strLen {
		return str + strings.Repeat(" ", newLen-strLen)
	} else if newLen < strLen {
		return utf8Str.Slice(0, newLen-1) + dots
	}
	return str
}

// proper match for path is when whole directory is matched
// proper match for command is when term matches word delimited by whitespace
func properMatch(str, term, padChar string) bool {
	return strings.Contains(padChar+str+padChar, padChar+term+padChar)
}

func trimContent(Content string) string {
	return strings.TrimRightFunc(Content, unicode.IsSpace)
}

func replaceNewLines(Content string) string {
	return strings.ReplaceAll(Content, "\n", "\\n ")
}

// NewItemFromRecordForQuery creates new item from record based on given query
//
//	returns error if the query doesn't match the record
func NewItemFromRecordForQuery(record SearchApp, query Query, debug bool) (Item, error) {

	const timeScoreCoef = 1e-13

	trimmedContent := trimContent(record.Content)

	key := trimmedContent

	cmd := trimmedContent

	Content := replaceNewLines(trimmedContent)
	ContentWithColor := replaceNewLines(cmd)

	keyInt, _ := strconv.Atoi(key)
	if record.IsRaw {
		return Item{
			isRaw:            true,
			ContentOut:       record.Content,
			Content:          Content,
			ContentWithColor: ContentWithColor,
			Idx:              keyInt,
		}, nil
	}

	it := Item{
		time:             record.Time,
		ContentOut:       record.Content,
		Content:          Content,
		ContentWithColor: ContentWithColor,
		Idx:              keyInt,
	}
	return it, nil
}

func GetHeader(compactRendering bool) ItemColumns {
	date := "TIME "
	Content := "CONTENT"
	return ItemColumns{
		Date:             date,
		DateWithColor:    date,
		Content:          Content,
		ContentWithColor: Content,
		Key:              "_HEADERS_",
	}
}

type RawItem struct {
	ContentWithColor string
	Content          string
	ContentOut       string

	Idx   int
	Score float64
	Key   string
}

type RawItems []RawItem

func (e RawItems) String(i int) string {
	return e[i].Content
}

func (e RawItems) Len() int {
	return len(e)
}

func NewRawItemFromRecordForQuery(idx int, record SearchApp, terms []string, debug bool) (RawItem, error) {
	const hitScore = 1.0
	const hitScoreConsecutive = 0.01
	const properMatchScore = 0.3

	const timeScoreCoef = 1e-13

	trimmedContent := strings.TrimRightFunc(record.Content, unicode.IsSpace)

	key := trimmedContent

	score := 0.0
	cmd := trimmedContent
	for _, term := range terms {
		c := strings.Count(record.Content, term)
		if c > 0 {
			score += hitScore + hitScoreConsecutive*float64(c)
			if properMatch(cmd, term, " ") {
				score += properMatchScore
			}
			cmd = strings.ReplaceAll(cmd, term, highlightMatch(term))
		}
	}
	score += record.Time * timeScoreCoef

	Content := replaceNewLines(trimmedContent)
	ContentWithColor := replaceNewLines(cmd)

	it := RawItem{
		ContentOut:       record.Content,
		Content:          Content,
		ContentWithColor: ContentWithColor,
		Score:            score,
		Key:              key,
		Idx:              idx,
	}
	return it, nil
}
