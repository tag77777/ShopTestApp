package a77777_888.me.t.shoptestapp.data.remote.entities

import a77777_888.me.t.shoptestapp.R

data class Item(
    val available: Int,
    val description: String,
    val feedback: Feedback,
    val id: String,
    val info: List<Info>,
    val ingredients: String,
    val price: Price,
    val subtitle: String,
    val tags: List<String>,
    val title: String
) {
    val images
        get() = when (id) {
            "cbf0c984-7c6c-4ada-82da-e29dc698bb50" -> listOf(R.drawable.p6, R.drawable.p5)
            "54a876a5-2205-48ba-9498-cfecff4baa6e" -> listOf(R.drawable.p1, R.drawable.p2)
            "75c84407-52e1-4cce-a73a-ff2d3ac031b3" -> listOf(R.drawable.p5, R.drawable.p6)
            "16f88865-ae74-4b7c-9d85-b68334bb97db" -> listOf(R.drawable.p3, R.drawable.p4)
            "26f88856-ae74-4b7c-9d85-b68334bb97db" -> listOf(R.drawable.p2, R.drawable.p3)
            "15f88865-ae74-4b7c-9d81-b78334bb97db" -> listOf(R.drawable.p6, R.drawable.p1)
            "88f88865-ae74-4b7c-9d81-b78334bb97db" -> listOf(R.drawable.p4, R.drawable.p3)
            "55f58865-ae74-4b7c-9d81-b78334bb97db" -> listOf(R.drawable.p1, R.drawable.p5)
            else -> listOf(R.drawable.p6, R.drawable.p5)
        }

}