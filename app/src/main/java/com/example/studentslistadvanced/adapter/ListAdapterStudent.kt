package com.example.studentslistadvanced.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.studentslistadvanced.databinding.RowStudentBinding
import com.example.studentslistadvanced.delete.IDelete
import com.example.studentslistadvanced.fragments.list.ListFragmentDirections
import com.example.studentslistadvanced.model.Student

class ListAdapterStudent(private val delete: IDelete) :
    RecyclerView.Adapter<ListAdapterStudent.MyViewHolder>() {

    private var studentList = mutableListOf<Student>()

    fun setStudentList(list: MutableList<Student>) {
        this.studentList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: RowStudentBinding =
            RowStudentBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentStudent = studentList[position]
        holder.setStudentInfo(currentStudent)
    }

    override fun getItemCount(): Int {
        if (studentList.size == 0)
            return 0
        return studentList.size
    }

    inner class MyViewHolder(val mBinding: RowStudentBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun setStudentInfo(currentStudent: Student) {
            with(mBinding) {
                firstName.text = currentStudent.firstName.capitalize()
                idStudent.text = currentStudent.id.toString()

                deleteI.setOnClickListener {
                    delete.delete(currentStudent)
                    notifyDataSetChanged()
                }

                constraintLayoutSt.setOnClickListener {
                    val action =
                        ListFragmentDirections.actionListFragmentToStudenFragment(currentStudent)
                    itemView.findNavController().navigate(action)
                }
            }
        }
    }


}