package com.nsnik.nrs.jaron.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TagEntity {

    @PrimaryKey
    var tagValue: String = ""

}