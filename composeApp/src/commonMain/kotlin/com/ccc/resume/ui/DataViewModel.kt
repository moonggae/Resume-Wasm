package com.ccc.resume.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.resume.resources.Res
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.MissingResourceException

class DataViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<DataUiState> = MutableStateFlow(DataUiState.Loading)
    val uiState: StateFlow<DataUiState> = _uiState

    init {
        loadDataResource()
    }

    @OptIn(ExperimentalResourceApi::class)
    fun loadDataResource() {
        viewModelScope.launch {
            runCatching {
                val bytes = Res.readBytes("files/form.json")
                Json { ignoreUnknownKeys = true }
                    .decodeFromString<DataUiState.Success>(bytes.decodeToString())
            }.onSuccess {
                _uiState.value = it
            }.onFailure { exception ->
                when (exception) {
                    is MissingResourceException -> {}
                    is SerializationException -> {}
                    is IllegalArgumentException -> {}
                }
                println(exception)
                _uiState.value = DataUiState.Fail(exception)
            }
        }
    }
}
