package net.faracloud.dashboard.features.sensorDetails.chart

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.model.GradientColor
import com.github.mikephil.charting.utils.*
import kotlinx.android.synthetic.main.row_observation.view.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.features.sensorDetails.custom.DayAxisValueFormatter

//private val rows: List<IRow>
class ChartsAdapter(private val obsevations: List<Int>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    companion object {
        private const val TYPE_BAR = 0
        private const val TYPE_CircularProgress = 1
        private const val TYPE_CubicLines = 2
        private const val TYPE_Pie = 3
    }

    interface IRow
    class CircularProgressRow(val date: String, val title: String) : IRow
    class BarChartRow(val message: String) : IRow
    class CubicLinesRow(val colour: Int) : IRow
    class PieRow(val colour: Int) : IRow

    class BarChartViewHolder(itemView: View) : ViewHolder(itemView) {
        val barChart: BarChart = itemView.findViewById(R.id.barChart)
    }

    class  CircularProgressViewHolder(itemView: View) : ViewHolder(itemView) {
        //val circularProgressTitle: TextView = itemView.findViewById(R.id.valueTitle)
    }

    class  CubicLinesViewHolder(itemView: View) : ViewHolder(itemView) {
        //val cubicLinesTitle: TextView = itemView.findViewById(R.id.valueTitle)
        val lineChart: LineChart = itemView.findViewById(R.id.lineChart)
    }

    class  PieViewHolder(itemView: View) : ViewHolder(itemView) {
        val pieChart: PieChart = itemView.findViewById(R.id.pieChart)
    }

    override fun getItemCount() = 4 //rows.count()

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> TYPE_Pie
            1  -> TYPE_BAR
            2 -> TYPE_CubicLines
            3 -> TYPE_CircularProgress
            else -> throw IllegalArgumentException()
        }
        /*when (rows[position]) {
            is BarChartRow  -> TYPE_BAR
            is CircularProgressRow -> TYPE_CircularProgress
            is CubicLinesRow -> TYPE_CubicLines
            is PieRow -> TYPE_Pie
            else -> throw IllegalArgumentException()
        }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_BAR -> BarChartViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.row_chart_bar, parent, false))

        TYPE_CircularProgress -> CircularProgressViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.row_circular_progress, parent, false))

        TYPE_CubicLines -> CubicLinesViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.row_chart_line, parent, false))

        TYPE_Pie -> PieViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.row_chart_pie, parent, false))

        else -> throw IllegalArgumentException()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder.itemViewType) {
            TYPE_BAR -> onBindBarChart(holder)
            TYPE_CircularProgress -> onBindCircularProgress(holder)
            TYPE_CubicLines -> onBindCubicLines(holder)
            TYPE_Pie -> onBindPie(holder)
            else -> throw IllegalArgumentException()
/*            TYPE_BAR -> onBindBarChart(holder, rows[position] as ChartsAdapter.BarChartRow)
            TYPE_CircularProgress -> onBindCircularProgress(holder, rows[position] as ChartsAdapter.CircularProgressRow)
            TYPE_CubicLines -> onBindCubicLines(holder, rows[position] as ChartsAdapter.CubicLinesRow)
            TYPE_Pie -> onBindPie(holder, rows[position] as ChartsAdapter.PieRow)
            else -> throw IllegalArgumentException()*/
        }

    private fun onBindBarChart(holder: RecyclerView.ViewHolder) {
        val barChartViewHolder = holder as BarChartViewHolder
        //barChartViewHolder.barTitle.text = "bar"
        barChartViewHolder.run {
            //barChart.setOnChartValueSelectedListener(this)
            barChart.setDrawBarShadow(false)
            barChart.setDrawValueAboveBar(true)

            barChart.getDescription().setEnabled(false)

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
            barChart.setMaxVisibleValueCount(60)

            // scaling can now only be done on x- and y-axis separately

            // scaling can now only be done on x- and y-axis separately
            barChart.setPinchZoom(false)

            barChart.setDrawGridBackground(false)
            // chart.setDrawYLabels(false);

            // chart.setDrawYLabels(false);
            val xAxisFormatter: IAxisValueFormatter = DayAxisValueFormatter(barChart)

            val xAxis: XAxis = barChart.getXAxis()
            xAxis.position = XAxisPosition.BOTTOM
            //xAxis.typeface = tfLight
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f // only intervals of 1 day

            xAxis.labelCount = 7
            //xAxis.setValueFormatter(xAxisFormatter as ValueFormatter?)

            //val custom: IAxisValueFormatter = MyAxisValueFormatter()

            val leftAxis: YAxis = barChart.getAxisLeft()
           // leftAxis.typeface = tfLight
            leftAxis.setLabelCount(8, false)
          //  leftAxis.setValueFormatter(custom as ValueFormatter?)
            leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
            leftAxis.spaceTop = 15f
            leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)


            val rightAxis: YAxis = barChart.getAxisRight()
            rightAxis.setDrawGridLines(false)
            //rightAxis.typeface = tfLight
            rightAxis.setLabelCount(8, false)
            //rightAxis.setValueFormatter(custom)
            rightAxis.spaceTop = 15f
            rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)


            val l: Legend = barChart.getLegend()
            l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            l.orientation = Legend.LegendOrientation.HORIZONTAL
            l.setDrawInside(false)
            l.form = LegendForm.SQUARE
            l.formSize = 9f
            l.textSize = 11f
            l.xEntrySpace = 4f

            //val mv = XYMarkerView(this.itemView.context, xAxisFormatter)
           // mv.setChartView(barChart) // For bounds control

            //barChart.setMarker(mv) // Set the marker to the chart


            // setting data

            // setting data

            var count: Int = 5
            var range: Float = 45F
            val start = 1f
            val values = java.util.ArrayList<BarEntry>()
            var i = start.toInt()
            while (i < start + count) {
                val value = (Math.random() * (range + 1)).toFloat()
                if (Math.random() * 100 < 25) {
                    values.add(BarEntry(i.toFloat(), value, this.itemView.context.getResources().getDrawable(R.drawable.star)))
                } else {
                    values.add(BarEntry(i.toFloat(), value))
                }
                i++
            }
            val set1: BarDataSet
            if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0
            ) {
                set1 = barChart.getData().getDataSetByIndex(0) as BarDataSet
                set1.values = values
                barChart.getData().notifyDataChanged()
                barChart.notifyDataSetChanged()
            } else {
                set1 = BarDataSet(values, "The year 2017")
                set1.setDrawIcons(false)
                val startColor1 = ContextCompat.getColor(this.itemView.context, android.R.color.holo_orange_light)
                val startColor2 = ContextCompat.getColor(this.itemView.context, android.R.color.holo_blue_light)
                val startColor3 = ContextCompat.getColor(this.itemView.context, android.R.color.holo_orange_light)
                val startColor4 = ContextCompat.getColor(this.itemView.context, android.R.color.holo_green_light)
                val startColor5 = ContextCompat.getColor(this.itemView.context, android.R.color.holo_red_light)
                val endColor1 = ContextCompat.getColor(this.itemView.context, android.R.color.holo_blue_dark)
                val endColor2 = ContextCompat.getColor(this.itemView.context, android.R.color.holo_purple)
                val endColor3 = ContextCompat.getColor(this.itemView.context, android.R.color.holo_green_dark)
                val endColor4 = ContextCompat.getColor(this.itemView.context, android.R.color.holo_red_dark)
                val endColor5 = ContextCompat.getColor(this.itemView.context, android.R.color.holo_orange_dark)
                val gradientFills: MutableList<GradientColor> = java.util.ArrayList<GradientColor>()
                gradientFills.add(GradientColor(startColor1, endColor1))
                gradientFills.add(GradientColor(startColor2, endColor2))
                gradientFills.add(GradientColor(startColor3, endColor3))
                gradientFills.add(GradientColor(startColor4, endColor4))
                gradientFills.add(GradientColor(startColor5, endColor5))
                set1.setGradientColors(gradientFills) //  setFills(gradientFills)
                val dataSets = java.util.ArrayList<IBarDataSet>()
                dataSets.add(set1)
                val data = BarData(dataSets)
                data.setValueTextSize(10f)
                //data.setValueTypeface(tfLight)
                data.barWidth = 0.9f
                barChart.setData(data)
            }

            // chart.setDrawLegend(false);
        }
    }

    //
    private fun onBindCircularProgress(holder: RecyclerView.ViewHolder) {
        val circularProgressViewHolder = holder as CircularProgressViewHolder
       // circularProgressViewHolder.circularProgressTitle.text = "circularProgressTitle"
    }

    private fun onBindCubicLines(holder: RecyclerView.ViewHolder) {
        val cubicLinesViewHolder = holder as CubicLinesViewHolder
        //cubicLinesViewHolder.cubicLinesTitle.text = "cubicLinesTitle"

        //cubicLinesViewHolder.lineChart = findViewById(R.id.chart1)
        cubicLinesViewHolder.run {
            lineChart.setViewPortOffsets(0f, 0f, 0f, 0f)
            lineChart.setBackgroundColor(Color.rgb(104, 241, 175))

            // no description text

            // no description text
            lineChart.getDescription().setEnabled(false)

            // enable touch gestures

            // enable touch gestures
            lineChart.setTouchEnabled(true)

            // enable scaling and dragging

            // enable scaling and dragging
            lineChart.setDragEnabled(true)
            lineChart.setScaleEnabled(true)

            // if disabled, scaling can be done on x- and y-axis separately

            // if disabled, scaling can be done on x- and y-axis separately
            lineChart.setPinchZoom(false)

            lineChart.setDrawGridBackground(false)
            lineChart.setMaxHighlightDistance(300f)

            val x: XAxis = lineChart.getXAxis()
            x.isEnabled = false

            val y: YAxis = lineChart.getAxisLeft()
            //y.typeface = tfLight
            y.setLabelCount(6, false)
            y.textColor = Color.WHITE
            y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
            y.setDrawGridLines(false)
            y.axisLineColor = Color.WHITE

            lineChart.getAxisRight().setEnabled(false)

            // add data



            // lower max, as cubic runs significantly slower than linear

            // lower max, as cubic runs significantly slower than linear


            lineChart.getLegend().setEnabled(false)

            lineChart.animateXY(2000, 2000)

            // don't forget to refresh the drawing

            // don't forget to refresh the drawing
            lineChart.invalidate()

            //set data
            var count: Int = 5
            var range: Float = 45F
            val values = ArrayList<Entry>()
            for (i in 0 until count) {
                val `val` = (Math.random() * (range + 1)).toFloat() + 20
                values.add(Entry(i.toFloat(), `val`))
            }
            val set1: LineDataSet
            if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0
            ) {
                set1 = lineChart.getData().getDataSetByIndex(0) as LineDataSet
                set1.values = values
                lineChart.getData().notifyDataChanged()
                lineChart.notifyDataSetChanged()
            } else {
                // create a dataset and give it a type
                set1 = LineDataSet(values, "DataSet 1")
                set1.mode = LineDataSet.Mode.CUBIC_BEZIER
                set1.cubicIntensity = 0.2f
                set1.setDrawFilled(true)
                set1.setDrawCircles(false)
                set1.lineWidth = 1.8f
                set1.circleRadius = 4f
                set1.setCircleColor(Color.WHITE)
                set1.highLightColor = Color.rgb(244, 117, 117)
                set1.color = Color.WHITE
                set1.fillColor = Color.WHITE
                set1.fillAlpha = 100
                set1.setDrawHorizontalHighlightIndicator(false)
                //set1.fillFormatter =   IFillFormatter { dataSet, dataProvider -> lineChart.getAxisLeft().getAxisMinimum() }

                // create a data object with the data sets
                val data = LineData(set1)
                //data.setValueTypeface(tfLight)
                data.setValueTextSize(9f)
                data.setDrawValues(false)

                // set data
                lineChart.setData(data)
            }
        }

    }

    private fun onBindPie(holder: RecyclerView.ViewHolder) {
        val pieViewHolder = holder as PieViewHolder
       // pieViewHolder.pieTitle.text = "pieViewHolder"
        pieViewHolder.run {
            pieChart.setUsePercentValues(true)
            pieChart.getDescription().setEnabled(false)
            pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

            pieChart.setDragDecelerationFrictionCoef(0.95f)

//            pieChart.setCenterTextTypeface(tfLight)
//            pieChart.setCenterText(generateCenterSpannableText())

            pieChart.setDrawHoleEnabled(true)
            pieChart.setHoleColor(Color.WHITE)

            pieChart.setTransparentCircleColor(Color.WHITE)
            pieChart.setTransparentCircleAlpha(110)

            pieChart.setHoleRadius(58f)
            pieChart.setTransparentCircleRadius(61f)

            pieChart.setDrawCenterText(true)

            pieChart.setRotationAngle(0f)
            // enable rotation of the chart by touch
            // enable rotation of the chart by touch
            pieChart.setRotationEnabled(true)
            pieChart.setHighlightPerTapEnabled(true)

            // chart.setUnit(" €");
            // chart.setDrawUnitsInChart(true);

            // add a selection listener

            // chart.setUnit(" €");
            // chart.setDrawUnitsInChart(true);

            // add a selection listener
            //pieChart.setOnChartValueSelectedListener(this)

            pieChart.animateY(1400, Easing.EaseInOutQuad)
            // chart.spin(2000, 0, 360);

            // chart.spin(2000, 0, 360);
            val l: Legend = pieChart.getLegend()
            l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            l.orientation = Legend.LegendOrientation.VERTICAL
            l.setDrawInside(false)
            l.xEntrySpace = 7f
            l.yEntrySpace = 0f
            l.yOffset = 0f

            // entry label styling

            // entry label styling
            pieChart.setEntryLabelColor(Color.WHITE)
            //pieChart.setEntryLabelTypeface(tfRegular)
            pieChart.setEntryLabelTextSize(12f)

            val entries = java.util.ArrayList<PieEntry>()
            val months = arrayOf(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
            )

            val parties = arrayOf(
                "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
                "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
                "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
                "Party Y", "Party Z"
            )
            // NOTE: The order of the entries when being added to the entries array determines their position around the center of
            // the chart.
            var count: Int = 5
            var range: Float = 45F
            for (i in 0 until count) {
                entries.add(
                    PieEntry(
                        (Math.random() * range + range / 5).toFloat(),
                        parties.get(i % parties.size),
                        this.itemView.context.getResources().getDrawable(R.drawable.star)
                    )
                )
            }
            val dataSet = PieDataSet(entries, "Election Results")
            dataSet.setDrawIcons(false)
            dataSet.sliceSpace = 3f
            dataSet.iconsOffset = MPPointF(0F, 40F)
            dataSet.selectionShift = 5f

            // add a lot of colors
            val colors = java.util.ArrayList<Int>()
            for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
            for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
            for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
            for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
            for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
            colors.add(ColorTemplate.getHoloBlue())
            dataSet.colors = colors
            //dataSet.setSelectionShift(0f);
            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(11f)
            data.setValueTextColor(Color.WHITE)
            //data.setValueTypeface(tfLight)
            pieChart.setData(data)

            // undo all highlights
            pieChart.highlightValues(null)
            pieChart.invalidate()


        }

    }

    private fun setData(count: Int, range: Float) {

    }


    fun createCubicLineChart() {

    }

    ///
}