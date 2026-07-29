package gocuisearch

import (
	"sort"
	"strings"
)

type Query struct {
	terms []string
}

func isValidTerm(term string) bool {
	if len(term) == 0 {
		return false
	}
	if strings.Contains(term, " ") {
		return false
	}
	return true
}

func filterTerms(terms []string) []string {
	var newTerms []string
	for _, term := range terms {
		if isValidTerm(term) {
			newTerms = append(newTerms, term)
		}
	}
	return newTerms
}

func NewQueryFromString(queryInput string, host string, pwd string, gitOriginRemote string, debug bool) Query {
	terms := strings.Fields(queryInput)
	var logStr string
	for _, term := range terms {
		logStr += " <" + term + ">"
	}
	terms = filterTerms(terms)
	logStr = ""
	for _, term := range terms {
		logStr += " <" + term + ">"
	}
	sort.SliceStable(terms, func(i, j int) bool { return len(terms[i]) < len(terms[j]) })
	return Query{
		terms: terms,
	}
}

func GetRawTermsFromString(queryInput string, debug bool) []string {
	terms := strings.Fields(queryInput)
	var logStr string
	for _, term := range terms {
		logStr += " <" + term + ">"
	}
	terms = filterTerms(terms)
	logStr = ""
	for _, term := range terms {
		logStr += " <" + term + ">"
	}
	return terms
}
