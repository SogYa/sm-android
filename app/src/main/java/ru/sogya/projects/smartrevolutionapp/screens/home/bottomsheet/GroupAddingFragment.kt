package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentAddGroupBinding

class GroupAddingFragment : Fragment(R.layout.fragment_add_group) {
    private lateinit var binding: FragmentAddGroupBinding
    private val vm: GroupAddingVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            val groupTag = binding.groupTag.text.toString()
            val groupDesc = binding.groupDesc.text.toString()
            if (groupTag.isNotEmpty()) {
                vm.createNewGroup(groupTag, groupDesc)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}