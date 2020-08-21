In Fluper take all data from a json file named product_list.json
every time user get into the application, it will remove all the data from db and you can start from adding data into the db
I also included the screenshots

/***** Additional Libraries *****/

    //to handle json file
    implementation 'com.google.code.gson:gson:2.8.5'

    //using room db to store data
    implementation 'android.arch.persistence.room:runtime:1.1.1'
    annotationProcessor 'android.arch.persistence.room:compiler:1.1.1'

    //handle images
    implementation 'com.github.bumptech.glide:glide:4.10.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'

    //search in product list
    implementation 'com.github.simonebortolin:MaterialSearchBar:0.10'

    //used to zoom the image
    implementation 'com.github.hsmnzaydn:imagezoom:1.3.0'