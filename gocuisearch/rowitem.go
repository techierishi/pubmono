package gocuisearch

type RowItem struct {
	Deleted  bool   `json:"deleted,omitempty"`
	Favorite bool   `json:"favorite,omitempty"`
	Time     string `json:"time,omitempty"`
	ExitCode int    `json:"exitCode,omitempty"`

	Idx     int    `json:"idx"`
	Content string `json:"content"`
}
