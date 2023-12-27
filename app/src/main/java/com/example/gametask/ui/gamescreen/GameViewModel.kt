import android.os.CountDownTimer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _remainingTime = mutableStateOf(0)
    val remainingTime: State<Int> = _remainingTime

    private val _currentCoins = mutableStateOf(100)
    val currentCoins: State<Int> = _currentCoins

    private val _isTimerRunning = mutableStateOf(false)
    val isTimerRunning: State<Boolean> = _isTimerRunning

    private val _isGameOver = mutableStateOf(false)
    val isGameOver: State<Boolean> = _isGameOver

    private val _earnedCoins = mutableStateOf(0)
    val earnedCoins: State<Int> = _earnedCoins


    private var timer: CountDownTimer? = null

    init {
        startTimer()
    }

    fun startTimer() {
        if (!isTimerRunning.value) {
            _isTimerRunning.value = true
            timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
                override fun onTick(p0: Long) {
                    if (_isTimerRunning.value && !_isGameOver.value) {
                        _remainingTime.value++
                        if (_currentCoins.value > 10 && _remainingTime.value > 20) {
                            _currentCoins.value = maxOf(10, _currentCoins.value - 5)
                        }
                        _earnedCoins.value = calculateEarnedCoins()
                    }
                }

                override fun onFinish() {
                    _isGameOver.value = true
                    _earnedCoins.value = calculateEarnedCoins()
                }
            }
            timer?.start()
        }
    }

    fun calculateEarnedCoins(): Int {
        return _currentCoins.value
    }

    fun getEarnedCoins(): Int {
        return _earnedCoins.value
    }

    fun stopTimer() {
        timer?.cancel()
        _isTimerRunning.value = false
        //_earnedCoins.value = 0
    }
}