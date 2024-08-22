package it2161.assignment2.movieviewerbasic_starter

import android.app.Application
import it2161.assignment2.movieviewerbasic_starter.entity.SimpleMovieItem


class MovieViewerApplication(): Application(){

    companion object {

        var movieItemArrays: ArrayList<SimpleMovieItem>?=null
        val appInstance = MovieViewerApplication()

    }



}