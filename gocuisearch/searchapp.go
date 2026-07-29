package gocuisearch

type SearchApp struct {
	IsRaw   bool
	Content string
	Time    float64
	Idx     int
}

func NewSearchAppFromCmdLine(cmdLine string) SearchApp {
	return SearchApp{
		IsRaw:   true,
		Content: cmdLine,
	}
}

func NewSearchApp(r *RowItem) SearchApp {
	return SearchApp{
		IsRaw:   false,
		Idx:     r.Idx,
		Content: r.Content,
	}
}
