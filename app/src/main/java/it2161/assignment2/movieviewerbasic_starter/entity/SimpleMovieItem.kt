package it2161.assignment2.movieviewerbasic_starter.entity

class SimpleMovieItem(
    var overview: String? = null,
    var release_date: String? = null,
    var original_langauge: String? = null,
    var title: String? = null
) {


    init {

        this.overview = overview
        this.release_date = release_date
        this.original_langauge = original_langauge
        this.title = title

    }

    override fun toString(): String {
        return this.title?: "Unknown Title"
    }

}