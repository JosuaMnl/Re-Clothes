import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?,

    @SerializedName("roles")
    val roles: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("phone_number")
    val phoneNumber: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
)

data class Data(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("user")
    val user: User
)

data class Meta(
    @SerializedName("code")
    val code: Int,

    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String
)

data class LoginRegisterResponse(
    @SerializedName("meta")
    val meta: Meta,

    @SerializedName("data")
    val data: Data
)