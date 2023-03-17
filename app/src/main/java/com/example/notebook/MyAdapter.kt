import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.ElemNote
import com.example.notebook.R

class MyAdapter(private val mass: MutableList<ElemNote>,private val onItemClicked: (position: Int) -> Unit,private val onItemLongClicked: (position: Int) -> Unit,private val onChClicked: (position: Int) -> Unit)
    : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View,private val onItemClicked: (position: Int) -> Unit,private val onItemLongClicked: (position: Int) -> Unit,private val onChClicked: (position: Int) -> Unit)
        : RecyclerView.ViewHolder(itemView),View.OnClickListener, View.OnLongClickListener{
        val theme:TextView = itemView.findViewById(R.id.title)
        val date = itemView.findViewById(R.id.date) as TextView
        val subtitleText = itemView.findViewById(R.id.description) as TextView
        val chbox = itemView.findViewById(R.id.checkBox1) as CheckBox
        val clickable = itemView.findViewById(R.id.clickable_part) as View
        init {
            clickable.setOnClickListener(this)
            clickable.setOnLongClickListener(this)
            chbox.setOnClickListener {
                this.onClick(it,1)
            }
        }
        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)
        }
        fun onClick(v: View,a:Int) {
            val position = adapterPosition
            onChClicked(position)
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            onItemLongClicked(position)
            return true
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.elem, parent, false)
        return MyViewHolder(itemView,onItemClicked,onItemLongClicked,onChClicked)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.theme.text = mass[position].theme
        holder.date.text = mass[position].date
        holder.subtitleText.text = mass[position].desc
        holder.chbox.isChecked = mass[position].fav
    }

    override fun getItemCount() = mass.size
}

