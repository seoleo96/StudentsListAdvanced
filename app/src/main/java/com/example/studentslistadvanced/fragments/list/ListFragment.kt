package com.example.studentslistadvanced.fragments.list

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.studentslistadvanced.R
import com.example.studentslistadvanced.adapter.ListAdapterStudent
import com.example.studentslistadvanced.databinding.FragmentListBinding
import com.example.studentslistadvanced.db.SimpleDB
import com.example.studentslistadvanced.delete.IDelete
import com.example.studentslistadvanced.di.DI
import com.example.studentslistadvanced.model.Student
import com.google.android.material.snackbar.Snackbar


class ListFragment : Fragment(), IDelete {

    private var _binding: FragmentListBinding? = null
    private val mBinding get() = _binding!!

    private var list = mutableListOf<Student>()
    private lateinit var adapter: ListAdapterStudent
    private lateinit var db: SimpleDB
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        db = DI().db
        createAdapter()

        mBinding.button.setOnClickListener {
            addStudent()
        }

        setHasOptionsMenu(true)

        return mBinding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.restore -> {
                val bool = db.restoreStudent()
                return if (bool) {
                    adapter.setStudentList(getStudentSortList())
                    Snackbar.make(
                        requireView(),
                        "Restored! List Size ${db.getRestoreListSize().toString()}",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    true
                } else {
                    Snackbar.make(requireView(), "List is empty", Snackbar.LENGTH_SHORT)
                        .show()
                    false
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun createAdapter() {
        adapter = ListAdapterStudent(this)
        recyclerView = mBinding.recyclerView
        recyclerView.adapter = adapter
        adapter.setStudentList(getStudentSortList())
    }

    private fun addStudent() {
        val name = mBinding.firstName.text.trim().toString()
        if (name.isNotEmpty()) {
            if (!db.containsStudent(name)) {
                val student = Student(name, id = db.incrementId())
                db.addStudent(name, student)
                adapter.setStudentList(getStudentSortList())
                adapter.notifyDataSetChanged()
                clearEditText()
            } else {
                showToast(requireContext(), "There is student with name ${name}")
            }
        } else {
            showToast(requireContext(), "Empty field!")
        }
    }

    private fun clearEditText() {
        mBinding.firstName.text.clear()
    }


    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun delete(student: Student) {
        db.deleteStudent(student)
        val sortList = getStudentSortList()
        adapter.setStudentList(sortList)
    }

    private fun getStudentSortList(): MutableList<Student> {
        list = db.getList().values.toMutableList()
        list.sortBy { it.id }
        return list
    }

}