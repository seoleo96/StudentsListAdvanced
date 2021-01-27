package com.example.studentslistadvanced.fragments.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.studentslistadvanced.databinding.FragmentStudenBinding

class StudentFragment : Fragment() {

    private val args by navArgs<StudentFragmentArgs>()
    private var _binding: FragmentStudenBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStudenBinding.inflate(layoutInflater, container, false)



        args.args.let {
            with(mBinding) {
                idtext.text = it.id.toString()
                name.text = it.firstName.toString()
                surename.text = it.lastName ?: "No Data"
                gradetext.text = if (it.grade != null) it.grade.toString() else "No Data"
            }
        }
        return mBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}