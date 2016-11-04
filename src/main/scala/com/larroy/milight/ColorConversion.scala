package com.larroy.milight

object ColorConversion {
  def max(a: Double, b: Double, c: Double): Double = {
    math.max(math.max(a,b),c)
  }
  def min(a: Double, b: Double, c: Double): Double = {
    math.min(math.min(a,b),c)
  }
  def to_hls(color: Color) =
    rgb_to_hls(color.r, color.g, color.b)

  def to_hue(color: Color): Int = {
    val (h,l,s) = rgb_to_hls(color.r / 255, color.g / 255, color.b / 255)
    hls_to_hue(h, l, s)
  }

  def rgb_to_hls(r: Double, g: Double, b: Double): (Double, Double, Double) = {
    require(r >= 0 && r <= 1)
    require(g >= 0 && g <= 1)
    require(b >= 0 && b <= 1)
    val maxc = max(r, g, b)
    val minc = min(r, g, b)
    val l = (minc + maxc) / 2.0
    if (minc == maxc)
      return (0.0, l, 0.0)

    val s = if (l <= 0.5)
      (maxc - minc) / (maxc + minc)
    else
      (maxc - minc) / (2.0 - maxc - minc)

    val rc = (maxc - r) / (maxc - minc)
    val gc = (maxc - g) / (maxc - minc)
    val bc = (maxc - b) / (maxc - minc)
    var h = if (r == maxc)
      bc - gc
    else if (g == maxc)
      2.0 + rc - bc
    else
      4.0 + gc - rc
    h = (h / 6.0) % 1.0
    (h, l, s)
  }
  def hls_to_hue(h: Double, l: Double, s: Double): Int = {
    val res = (((2/3.0 + 1 - h) % 1) * 256).toInt
    math.floor(res).toInt
  }
}

