package com.nazarrybickij.cocktailstrike.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.adapters.IngredientsInFilterAdapter
import com.nazarrybickij.cocktailstrike.entity.FilterEntity
import com.nazarrybickij.cocktailstrike.viewmodels.AllCocktailsViewModel
import kotlinx.android.synthetic.main.bottom_sheet_layout.view.*


class FilterBottomSheetFragment : BottomSheetDialogFragment() {
    private var mBehavior: BottomSheetBehavior<*>? = null
    private lateinit var listView: RecyclerView
    private lateinit var viewModel: AllCocktailsViewModel
    private lateinit var callbackIngredientAdapter: IngredientsInFilterAdapter.AdapterCallback
    private lateinit var list: List<String>
    private lateinit var adapter: IngredientsInFilterAdapter
    private lateinit var chipGroup: ChipGroup
    private lateinit var viewDialog: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        viewDialog = View.inflate(context, R.layout.bottom_sheet_layout, null)
        val linearLayout: LinearLayout = viewDialog.findViewById(R.id.root)
        val params = linearLayout.layoutParams as LinearLayout.LayoutParams
        params.height = screenHeight
        linearLayout.layoutParams = params
        dialog.setContentView(viewDialog)
        mBehavior = BottomSheetBehavior.from<View>(viewDialog.parent as View)

        chipGroup = viewDialog.chip_group_view
        listView = viewDialog.list_ingredients
        listView.layoutManager = LinearLayoutManager(activity)

        listenerForHideKeyboard()

        viewDialog.back_view.setOnClickListener {
            dialog.onBackPressed()
        }
        viewDialog.apply_view.setOnClickListener {
            viewModel.selectedList.clear()
            viewModel.selectedList.addAll(adapter.selectedList)
            viewModel.mainFilter = FilterEntity(ingredients = adapter.selectedList.joinToString(separator = ","))
            dialog.onBackPressed()
        }
        viewDialog.search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                try {
                    adapter.filter.filter(newText)
                }catch (e:Exception){}

                return false
            }
        })
        viewDialog.search_view.setQuery("", true)
        return dialog
    }

    @SuppressLint("ClickableViewAccessibility")
    fun listenerForHideKeyboard(){
        viewDialog.list_ingredients.setOnTouchListener { v, event ->
            activity?.applicationContext?.let { it1 ->
                if (v != null) { hideKeyboardFrom(it1, v) }
            }
            v?.onTouchEvent(event) ?: true
        }
        viewDialog.setOnTouchListener { v, event ->
            activity?.applicationContext?.let { it1 ->
                if (v != null) { hideKeyboardFrom(it1, v) }
            }
            v?.onTouchEvent(event) ?: true
        }
    }
    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        @Suppress("UNCHECKED_CAST") val selList = viewModel.selectedList.clone() as ArrayList<String>
        callbackIngredientAdapter = object :
            IngredientsInFilterAdapter.AdapterCallback {
            override fun onIngredientClick(ingredient: String) {
                val chip = chipGroup.findViewWithTag<Chip>(ingredient)
                if (chip == null) {
                    Log.d("Chip", "add")
                    getChip(chipGroup, ingredient)
                } else {
                    Log.d("Chip", "remove")
                    chipGroup.removeView(chip)
                }
            }
        }
        try {
            if (adapter.values.isEmpty()) {
                list = viewModel.getListIngredients().value!!
                adapter.values = list as ArrayList<String>
            }
            adapter.callback = callbackIngredientAdapter
            for (it in selList) {
                getChip(chipGroup, it)
            }
            adapter.selectedList = selList
            listView.adapter = adapter
        }catch (e:Exception){
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AllCocktailsViewModel::class.java)
        try {
            adapter = IngredientsInFilterAdapter(viewModel.getListIngredients().value!!)
        } catch (e: Exception) {
        }
    }

    private fun getChip(chipGroup: ChipGroup, text: String): Chip? {
        val chip = LayoutInflater.from(requireContext())
            .inflate(R.layout.layout_chip_choice, chipGroup, false) as Chip
        chip.text = text
        chip.tag = text
        chip.setOnClickListener {
            chipGroup.removeView(chip)
            adapter.updateSelectedList(it.tag.toString())
        }
        chip.setOnCloseIconClickListener {
            chipGroup.removeView(chip)
            adapter.updateSelectedList(it.tag.toString())
        }
        chipGroup.addView(chip)
        return chip
    }

    companion object {
        val screenHeight: Int
            get() = Resources.getSystem().displayMetrics.heightPixels
    }
}

