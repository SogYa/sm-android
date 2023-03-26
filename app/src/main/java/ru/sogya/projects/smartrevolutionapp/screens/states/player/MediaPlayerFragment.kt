package ru.sogya.projects.smartrevolutionapp.screens.states.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sogya.domain.utils.Constants
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentStatePlayerBinding
import ru.sogya.projects.smartrevolutionapp.screens.states.StateSharedVM

class MediaPlayerFragment : BottomSheetDialogFragment(R.layout.fragment_state_player) {
    private val vm: StateSharedVM by viewModels()
    private lateinit var binding: FragmentStatePlayerBinding
    private var isMuted: Boolean? = false
    private var mediaPlayerState: String? = null

    companion object {
        private const val MEDIA_PAUSED = "paused"
        private const val MEDIA_PLAYING = "playing"
        private const val MEDIA_STOPPED = "stopped"
        private const val MEDIA_OFF = "off"
        private const val MEDIA_NEXT = "media_next_track"
        private const val MEDIA_PRIVIOUS = "media_previous_track"
        private const val MEDIA_PLAY = "media_play"
        private const val MEDIA_PAUSE = "media_pause"
        private const val MEDIA_TURN_ON = "turn_on"
        private const val MEDIA_TURN_OFF = "turn_off"
        private const val MEDIA_MUTE = "volume_mute"
        private const val MEDIA_VOLUME_UP = "volume_up"
        private const val MEDIA_VOLUME_DOWN = "volume_down"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatePlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        vm.getStateLiveData().observe(viewLifecycleOwner) {
            val volume = it.attributesDomain?.volumeLevel
            isMuted = it.attributesDomain?.isVolumeMuted
            mediaPlayerState = it.state
            binding.apply {
                playerId.text = it.attributesDomain?.friendlyName
                textViewTitle.text = it.attributesDomain?.mediaPlayerSongName
                textViewArtist.text = it.attributesDomain?.mediaPlayerArtistName
                Glide.with(requireContext()).load(it.attributesDomain?.entityPicture)
                    .into(imageViewMediPicture)
                when (mediaPlayerState) {
                    MEDIA_PAUSED -> imageButtonPlay.setImageResource(R.drawable.baseline_play_arrow_24)
                    MEDIA_PLAYING -> imageButtonPlay.setImageResource(R.drawable.baseline_pause_24)
                    MEDIA_STOPPED -> imageButtonPlay.setImageResource(R.drawable.baseline_stop_24)
                }
                when (isMuted) {
                    true -> buttonMute.setImageResource(R.drawable.baseline_volume_off_24)
                    else -> buttonMute.setImageResource(R.drawable.baseline_volume_mute_24)
                }
                durationProgressBar.max = it.attributesDomain?.mediaDuration!!.toInt()
                durationProgressBar.progress = it.attributesDomain?.mediaPosition!!.toInt()
                progressBarVolume.progress = (volume!! * 100).toInt()
                progressBarVolume.max = 100
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stateId = arguments?.getString(Constants.STATE_ID).toString()
        vm.getState(stateId)
        binding.apply {
            imageButtonNext.setOnClickListener {
                vm.callMediaPLayerService(stateId, MEDIA_NEXT)
            }
            imageButtonPrivios.setOnClickListener {
                vm.callMediaPLayerService(stateId, MEDIA_PRIVIOUS)
            }
            imageButtonPlay.setOnClickListener {
                if (mediaPlayerState == MEDIA_PLAYING) {
                    vm.callMediaPLayerService(stateId, MEDIA_PAUSE)
                } else {
                    vm.callMediaPLayerService(stateId, MEDIA_PLAY)
                }
            }
            buttonPower.setOnClickListener {
                if (mediaPlayerState == MEDIA_OFF) {
                    vm.callMediaPLayerService(stateId, MEDIA_TURN_ON)
                } else {
                    vm.callMediaPLayerService(stateId, MEDIA_TURN_OFF)
                }
            }
            buttonMute.setOnClickListener {
                if (!isMuted!!) {
                    vm.callMediaPLayerService(stateId, MEDIA_MUTE)
                } else {
                    vm.callMediaPLayerService(stateId, MEDIA_VOLUME_UP)
                }
            }
            buttonVolumeDown.setOnClickListener {
                vm.callMediaPLayerService(stateId, MEDIA_VOLUME_DOWN)
            }
            buttonVolumeUp.setOnClickListener {
                vm.callMediaPLayerService(stateId, MEDIA_VOLUME_UP)
            }
        }
    }
}