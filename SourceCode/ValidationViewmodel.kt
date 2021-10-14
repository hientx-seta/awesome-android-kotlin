class ValidationViewmodel : ViewModel() {

    private val _firstName = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _userID = MutableStateFlow("")

    fun setFirstName(name: String) {
        _firstName.value = name
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setUserId(id: String) {
        _userID.value = id
    }

    val isSubmitEnabled: Flow<Boolean> = combine(_firstName, _password, _userID) { firstName, password, userId ->
            val regexString = "[a-zA-Z]+"
            val isNameCorrect = firstName.matches(regexString.toRegex())
            val isPasswordCorrect = password.length > 8
            val isUserIdCorrect = userId.contains("_")
            return@combine isNameCorrect and isPasswordCorrect and isUserIdCorrect
        }
}

//And then in View, init this ViewModel, and listener change EditText to call function 

viewModel = ViewModelProvider(this).get(ValidationViewmodel::class.java)

private fun initListeners() {
    editText_name.addTextChangedListener {
        viewModel.setFirstName(it.toString())
    }
    editText_password.addTextChangedListener {
        viewModel.setPassword(it.toString())
    }
    editText_user.addTextChangedListener {
        viewModel.setUserId(it.toString())
    }
}
