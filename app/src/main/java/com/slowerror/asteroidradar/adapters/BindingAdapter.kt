package com.slowerror.asteroidradar.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.slowerror.asteroidradar.R
import com.slowerror.asteroidradar.models.PictureOfDay
import com.squareup.picasso.Picasso

@BindingAdapter("imageAsteroidStatus")
fun ImageView.bindImageAsteroidStatus(isHazardous: Boolean) {
    if (isHazardous) {
        this.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        this.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("imageAsteroidHazardous")
fun ImageView.bindImageAsteroidHazardous(isHazardous: Boolean) {
    if (isHazardous) {
        this.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        this.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("textAstronomicalUnitValue")
fun TextView.bindTextAstronomicalUnitValue(number: Double) {
    val context = this.context
    this.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("textEstimatedDiameterValue")
fun TextView.bindTextEstimatedDiameterValue(number: Double) {
    val context = this.context
    this.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("textRelativeVelocityValue")
fun TextView.bindTextRelativeVelocityValue(number: Double) {
    val context = this.context
    this.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("imageAsteroidOfDay")
fun ImageView.bindImageAsteroidOfDay(picture: PictureOfDay?) {
    Picasso.get()
        .load(picture?.url)
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.ic_baseline_broken_image)
        .fit()
        .centerCrop()
        .into(this)

}
