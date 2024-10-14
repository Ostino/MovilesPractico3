import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practico3mob.R

class PersonViewModel : ViewModel() {
    private val _personList = MutableLiveData<MutableList<String>>(
        mutableListOf()
    )
    val personList: LiveData<MutableList<String>> get() = _personList

    private val _likedPersons = MutableLiveData<MutableSet<String>>(mutableSetOf())
    val likedPersons: LiveData<MutableSet<String>> get() = _likedPersons

    private val _dislikedPersons = MutableLiveData<MutableSet<String>>(mutableSetOf())
    val dislikedPersons: LiveData<MutableSet<String>> get() = _dislikedPersons

    fun loadInitialPersonList() {
        _personList.value = mutableListOf("Chica", "Señor", "Señora")
    }

    fun likePerson(person: String) {
        if (_personList.value?.contains(person) == true) {
            _likedPersons.value?.add(person)
            _personList.value?.remove(person)
            notifyObservers()
        }
    }

    fun dislikePerson(person: String) {
        if (_personList.value?.contains(person) == true) {
            _dislikedPersons.value?.add(person)
            _personList.value?.remove(person)
            notifyObservers()
        }
    }

    private fun notifyObservers() {
        _personList.value = _personList.value
        _likedPersons.value = _likedPersons.value
        _dislikedPersons.value = _dislikedPersons.value
    }
    fun getPersonPhotos(person: String): List<Int> {
        return when (person) {
            "Chica" -> listOf(R.drawable.photo1a, R.drawable.photo1b, R.drawable.photo1c)
            "Señor" -> listOf(R.drawable.photo2a, R.drawable.photo2b, R.drawable.photo2b)
            "Señora" -> listOf(R.drawable.photo2c, R.drawable.photo1b, R.drawable.photo2b)
            else -> emptyList()
        }
    }
}