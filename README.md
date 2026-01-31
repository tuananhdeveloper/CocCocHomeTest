# Solution
- Using RecyclerView to display a list of articles.
- Using Retrofit (with Gson converter) to fetch data from News API and Podcast Index API.
- Using WebView to display articles.
- Using JavaScript to get audio source links.
- Using DownloadManager to download audio files.

# Code Architecture
- MVVM with Data Binding

# Issues
- Extracting audios file inside the articles:
Getting audio source links from some articles was difficult. The audio links were not always inside <audio> tags in every articles,
they could be inside <a> tags or <source> tags so I had to get articles from https://www.spreaker.com.
