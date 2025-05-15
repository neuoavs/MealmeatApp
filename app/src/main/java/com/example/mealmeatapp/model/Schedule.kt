package com.example.mealmeatapp.ui.theme.model

import com.example.mealmeatapp.R

data class Meal(
    val id: Int,
    val name: String,
    val imageResId: Int,
    val category: String,
    val calories: Int,
    val weight: Int, // gram
    val protein: Int, // gram
    val fat: Int,     // gram
    val carbs: Int,    // gram
    val ingredients: List<String>,
    val instructions: List<String>,
    val dayOfWeek: String = "" // Thêm thuộc tính này // Thêm thuộc tính này
)

class MealRepository {
    private val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    private val random = kotlin.random.Random
    private val meals = listOf(
        // Bữa sáng
        Meal(
            id = 1,
            name = "Bánh mì trứng",
            imageResId = R.drawable.pho_bo,
            category = "Breakfast",
            calories = 350,
            weight = 180,
            protein = 14,
            fat = 18,
            carbs = 28,
            ingredients = listOf(
                "1 ổ bánh mì.",
                "2 quả trứng gà.",
                "1 thìa hành lá băm nhỏ.",
                "Gia vị: muối, tiêu, hạt nêm.",
                "1 thìa dầu ăn."
            ),
            instructions = listOf(
                "Đánh tan trứng với chút muối, tiêu và hành lá.",
                "Làm nóng chảo với dầu ăn.",
                "Đổ trứng vào chiên lửa vừa đến khi chín vàng.",
                "Xẻ bánh mì, cho trứng vào giữa.",
                "Có thể thêm chút xì dầu hoặc tương ớt tùy thích."
            )
        ),
        Meal(
            id = 2,
            name = "Bún riêu",
            imageResId = R.drawable.pho_bo,
            category = "Breakfast",
            calories = 450,
            weight = 400,
            protein = 25,
            fat = 10,
            carbs = 50,
            ingredients = listOf(
                "300g bún tươi.",
                "200g cua đồng.",
                "100g thịt băm.",
                "1 miếng đậu hũ chiên.",
                "2 quả cà chua.",
                "Hành lá, rau thơm.",
                "Gia vị: mắm, muối, hạt nêm."
            ),
            instructions = listOf(
                "Giã cua, lọc lấy nước cua.",
                "Phi thơm hành tỏi, xào cà chua đến khi mềm.",
                "Cho nước cua vào đun sôi.",
                "Thả đậu hũ và thịt băm vào nồi.",
                "Nêm nếm vừa ăn.",
                "Trình bày với bún và rau thơm."
            )
        ),
        Meal(
            id = 3,
            name = "Phở bò",
            imageResId = R.drawable.pho_bo,
            category = "Breakfast",
            calories = 400,
            weight = 350,
            protein = 30,
            fat = 10,
            carbs = 45,
            ingredients = listOf(
                "300g bánh phở.",
                "200g thịt bò.",
                "Xương bò để nấu nước dùng.",
                "Hành tím, gừng, quế, hồi.",
                "Rau thơm: hành lá, ngò gai.",
                "Gia vị: muối, đường, nước mắm."
            ),
            instructions = listOf(
                "Hầm xương bò với gừng, hành tím, quế hồi.",
                "Thái mỏng thịt bò.",
                "Chần bánh phở qua nước sôi.",
                "Xếp thịt bò lên trên, chan nước dùng nóng.",
                "Thêm hành lá, rau thơm."
            )
        ),
        Meal(
            id = 4,
            name = "Xôi gà",
            imageResId = R.drawable.pho_bo,
            category = "Breakfast",
            calories = 380,
            weight = 300,
            protein = 22,
            fat = 10,
            carbs = 45,
            ingredients = listOf(
                "200g xôi.",
                "100g thịt gà.",
                "Hành phi."
            ),
            instructions = listOf(
                "Nấu xôi cho chín.",
                "Luộc thịt gà và xé nhỏ.",
                "Trộn xôi với thịt gà và hành phi."
            )
        ),
        Meal(
            id = 5,
            name = "Cháo trắng trứng muối",
            imageResId = R.drawable.pho_bo,
            category = "Breakfast",
            calories = 250,
            weight = 250,
            protein = 10,
            fat = 6,
            carbs = 35,
            ingredients = listOf(
                "1 chén gạo.",
                "500ml nước.",
                "2 quả trứng muối."
            ),
            instructions = listOf(
                "Nấu gạo với nước cho đến khi nhuyễn.",
                "Thái nhỏ trứng muối.",
                "Trình bày cháo với trứng muối."
            )
        ),
        Meal(
            id = 6,
            name = "Bánh cuốn",
            imageResId = R.drawable.pho_bo,
            category = "Breakfast",
            calories = 340,
            weight = 280,
            protein = 14,
            fat = 8,
            carbs = 40,
            ingredients = listOf(
                "200g bột gạo.",
                "300ml nước.",
                "Thịt băm, nấm hương, hành phi."
            ),
            instructions = listOf(
                "Pha bột với nước thành hỗn hợp sền sệt.",
                "Đổ bột vào chảo và hấp chín.",
                "Cuốn nhân thịt và nấm vào bánh."
            )
        ),
        Meal(
            id = 7,
            name = "Mì quảng",
            imageResId = R.drawable.pho_bo,
            category = "Breakfast",
            calories = 370,
            weight = 320,
            protein = 20,
            fat = 9,
            carbs = 45,
            ingredients = listOf(
                "200g mì quảng.",
                "100g thịt heo.",
                "100g tôm.",
                "Gia vị: nước mắm, tiêu."
            ),
            instructions = listOf(
                "Nấu nước dùng từ xương heo.",
                "Luộc tôm và thịt heo.",
                "Trình bày mì với nước dùng và rau sống."
            )
        ),
        Meal(
            id = 8,
            name = "Cơm gà",
            imageResId = R.drawable.pho_bo,
            category = "Lunch",
            calories = 450,
            weight = 350,
            protein = 28,
            fat = 20,
            carbs = 10,
            ingredients = listOf(
                "1 con gà.",
                "Gạo tẻ.",
                "Gia vị: muối, tiêu, nước mắm."
            ),
            instructions = listOf(
                "Luộc gà cho chín.",
                "Nấu cơm với nước luộc gà.",
                "Thái gà và trình bày cùng cơm."
            )
        ),
        Meal(
            id = 9,
            name = "Cá kho tộ",
            imageResId = R.drawable.pho_bo,
            category = "Lunch",
            calories = 400,
            weight = 350,
            protein = 30,
            fat = 12,
            carbs = 40,
            ingredients = listOf(
                "500g cá lóc.",
                "Gia vị: nước mắm, tiêu, đường."
            ),
            instructions = listOf(
                "Thái cá thành từng miếng.",
                "Ướp cá với gia vị.",
                "Kho cá trong nồi đất cho đến khi thấm."
            )
        ),
        Meal(
            id = 10,
            name = "Thịt kho trứng",
            imageResId = R.drawable.pho_bo,
            category = "Lunch",
            calories = 380,
            weight = 300,
            protein = 35,
            fat = 15,
            carbs = 8,
            ingredients = listOf(
                "400g thịt ba chỉ.",
                "4 quả trứng.",
                "Gia vị: nước mắm, tiêu."
            ),
            instructions = listOf(
                "Luộc trứng cho chín.",
                "Thái thịt và ướp gia vị.",
                "Kho thịt với trứng cho đến khi thấm."
            )
        ),
        Meal(
            id = 11,
            name = "Canh chua cá lóc",
            imageResId = R.drawable.pho_bo,
            category = "Lunch",
            calories = 300,
            weight = 300,
            protein = 25,
            fat = 6,
            carbs = 12,
            ingredients = listOf(
                "300g cá lóc.",
                "200g cà chua.",
                "Gia vị: me, nước mắm."
            ),
            instructions = listOf(
                "Nấu nước dùng với me.",
                "Thêm cá và cà chua vào nấu chín.",
                "Nêm nếm vừa ăn."
            )
        ),
        Meal(
            id = 12,
            name = "Gà kho gừng",
            imageResId = R.drawable.pho_bo,
            category = "Lunch",
            calories = 450,
            weight = 400,
            protein = 32,
            fat = 10,
            carbs = 50,
            ingredients = listOf(
                "500g gà.",
                "50g gừng.",
                "Gia vị: nước mắm, tiêu."
            ),
            instructions = listOf(
                "Thái gừng và ướp gà với gia vị.",
                "Kho gà với gừng cho đến khi thấm."
            )
        ),
        Meal(
            id = 13,
            name = "Cơm gà xối mỡ",
            imageResId = R.drawable.pho_bo,
            category = "Lunch",
            calories = 480,
            weight = 380,
            protein = 28,
            fat = 14,
            carbs = 48,
            ingredients = listOf(
                "1 con gà.",
                "Gạo tẻ.",
                "Gia vị: muối, tiêu."
            ),
            instructions = listOf(
                "Luộc gà cho chín.",
                "Nấu cơm và xối mỡ lên trên."
            )
        ),
        Meal(
            id = 14,
            name = "Bò xào rau củ",
            imageResId = R.drawable.pho_bo,
            category = "Lunch",
            calories = 400,
            weight = 360,
            protein = 24,
            fat = 8,
            carbs = 48,
            ingredients = listOf(
                "300g thịt bò.",
                "100g rau củ.",
                "Gia vị: nước mắm, tiêu."
            ),
            instructions = listOf(
                "Thái thịt bò và ướp gia vị.",
                "Xào thịt bò với rau củ cho đến khi chín."
            )
        ),
        Meal(
            id = 15,
            name = "Canh chua cá",
            imageResId = R.drawable.pho_bo,
            category = "Dinner",
            calories = 320,
            weight = 280,
            protein = 26,
            fat = 14,
            carbs = 10,
            ingredients = listOf(
                "300g cá.",
                "200g cà chua.",
                "Gia vị: me, nước mắm."
            ),
            instructions = listOf(
                "Nấu nước dùng với me.",
                "Thêm cá và cà chua vào nấu chín.",
                "Nêm nếm vừa ăn."
            )
        ),
        Meal(
            id = 16,
            name = "Lẩu gà lá é",
            imageResId = R.drawable.pho_bo,
            category = "Dinner",
            calories = 350,
            weight = 300,
            protein = 22,
            fat = 10,
            carbs = 8,
            ingredients = listOf(
                "1 con gà ta.",
                "100g lá é.",
                "Các loại rau nhúng lẩu.",
                "Sả, ớt, tỏi.",
                "Gia vị: muối, đường, hạt nêm."
            ),
            instructions = listOf(
                "Làm sạch gà, chặt miếng vừa ăn.",
                "Phi thơm sả tỏi, xào sơ gà.",
                "Cho nước vào đun sôi, thả lá é.",
                "Nêm gia vị vừa ăn.",
                "Nhúng rau và thịt gà khi ăn."
            )
        ),
        Meal(
            id = 17,
            name = "Cháo gà",
            imageResId = R.drawable.pho_bo,
            category = "Dinner",
            calories = 250,
            weight = 250,
            protein = 6,
            fat = 3,
            carbs = 20,
            ingredients = listOf(
                "200g gạo.",
                "300g thịt gà.",
                "Gia vị: muối, tiêu."
            ),
            instructions = listOf(
                "Nấu gạo với nước cho đến khi nhuyễn.",
                "Thêm thịt gà vào nấu chín.",
                "Nêm nếm vừa ăn."
            )
        ),
        Meal(
            id = 18,
            name = "Canh rau ngót thịt băm",
            imageResId = R.drawable.pho_bo,
            category = "Dinner",
            calories = 280,
            weight = 220,
            protein = 16,
            fat = 6,
            carbs = 24,
            ingredients = listOf(
                "200g rau ngót.",
                "100g thịt băm.",
                "Gia vị: muối, tiêu."
            ),
            instructions = listOf(
                "Nấu nước dùng.",
                "Thêm thịt băm vào nấu chín.",
                "Thêm rau ngót vào và nêm nếm."
            )
        ),
        Meal(
            id = 19,
            name = "Trứng chiên thịt",
            imageResId = R.drawable.pho_bo,
            category = "Dinner",
            calories = 400,
            weight = 350,
            protein = 30,
            fat = 10,
            carbs = 45,
            ingredients = listOf(
                "4 quả trứng.",
                "100g thịt băm.",
                "Gia vị: muối, tiêu."
            ),
            instructions = listOf(
                "Đánh trứng với gia vị.",
                "Xào thịt băm cho chín.",
                "Đổ trứng vào chảo và chiên."
            )
        ),
        Meal(
            id = 20,
            name = "Cá hấp hành gừng",
            imageResId = R.drawable.pho_bo,
            category = "Dinner",
            calories = 380,
            weight = 360,
            protein = 18,
            fat = 12,
            carbs = 55,
            ingredients = listOf(
                "300g cá.",
                "50g hành.",
                "50g gừng.",
                "Gia vị: muối, tiêu."
            ),
            instructions = listOf(
                "Thái hành và gừng.",
                "Ướp cá với gia vị.",
                "Hấp cá với hành và gừng."
            )
        ),
        Meal(
            id = 21,
            name = "Cơm rang trứng",
            imageResId = R.drawable.pho_bo,
            category = "Dinner",
            calories = 300,
            weight = 280,
            protein = 18,
            fat = 6,
            carbs = 20,
            ingredients = listOf(
                "2 chén cơm.",
                "2 quả trứng.",
                "Gia vị: muối, tiêu."
            ),
            instructions = listOf(
                "Đánh trứng và chiên.",
                "Thêm cơm vào xào.",
                "Nêm nếm vừa ăn."
            )
        ),
        Meal(
            id = 22,
            name = "Đậu hũ sốt cà chua",
            imageResId = R.drawable.pho_bo,
            category = "Dinner",
            calories = 350,
            weight = 350,
            protein = 28,
            fat = 12,
            carbs = 40,
            ingredients = listOf(
                "200g đậu hũ.",
                "100g cà chua.",
                "Gia vị: muối, đường."
            ),
            instructions = listOf(
                "Chiên đậu hũ cho vàng.",
                "Xào cà chua với gia vị.",
                "Thêm đậu hũ vào và nấu chín."
            )
        )
    )

    fun getMeals(category: String = "All"): List<Meal> {
        println("MealRepository: Fetching meals for category=$category")
        return if (category == "All") {
            meals
        } else {
            meals.filter { it.category == category }
        }.also {
            println("MealRepository: Returned meals=${it.map { it.id to it.name }}")
        }
    }

    fun getMealById(id: Int): Meal? {
        println("MealRepository: Fetching meal with id=$id")
        return meals.find { it.id == id }.also {
            println("MealRepository: Found meal=${it?.id to it?.name}")
        }
    }

    fun getRandomMeal(): Meal? {
        val selectedMeal = meals.randomOrNull()
        return selectedMeal?.copy(
            dayOfWeek = days.random(random)
        ).also {
            println("MealRepository: Random meal=${it?.id to it?.name}, dayOfWeek=${it?.dayOfWeek}")
        }
    }
}