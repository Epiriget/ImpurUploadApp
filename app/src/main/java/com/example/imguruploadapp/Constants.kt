package com.example.imguruploadapp

const val CLIENT_ID:String = "42976305e02068e"
const val CLIENT_SECRET:String = "03f1c41e2c906d58bd668119eeb5fdfecd8acc27"
const val TOKEN:String = "5ee84029d8a068225e1f87115da83c00ad9c25a7"
const val BEARER_PREFIX:String = "Bearer "

fun getAuthorizationHeader(): String = BEARER_PREFIX + TOKEN