package co.zw.amosesuwali.dogplayground.data

import co.zw.amosesuwali.dogplayground.models.Pictures

class PictureDataSource {
    fun loadPictures(): List<Pictures>{
        return listOf<Pictures>(
            Pictures( "https://images.dog.ceo/breeds/hound-basset/n02088238_11113.jpg"),
            Pictures( "https://images.dog.ceo/breeds/hound-basset/n02088238_11113.jpg"),
            Pictures( "https://images.dog.ceo/breeds/hound-basset/n02088238_11113.jpg"),
            Pictures( "https://images.dog.ceo/breeds/hound-basset/n02088238_11113.jpg")
        );
    }
}