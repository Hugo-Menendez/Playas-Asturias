import androidx.recyclerview.widget.DiffUtil

/*
Copyright (c) 2023 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Playa (

	val Nombre : String,
	val Informacion : String,
	val Zona : String,
	val InformacionTexto : String,
	val BanderaAzul : String,
	val QCalidad : String,
	val Concejo : String,
	val Accesos : String,
	val TipoDePlaya : String,
	val Servicios : String,
	val Longitud : String,
	val Observaciones : String,
	val MasInformacion : String,
	val RedesSociales : String,
	val Facebook : String,
	val Twitter : String,
	val Youtube : String,
	val Pinterest : String,
	val Instagram : String,
	val Rss : String,
	val OtrosCanales : String,
	val NombreCanal : String,
	val CanalUrl : String,
	val Geolocalizacion : String,
	val Coordenadas : String,
	val Visualizador : String,
	val Slide : String,
	val SlideTitulo : String,
	val SlideUrl : String
)
//Implementación del callback DifUtil.ItemCallback que permite al adaptador diferenciar un ítem de otro
{
	companion object {
		val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Playa>() {
			override fun areItemsTheSame(oldItem: Playa, newItem: Playa): Boolean {
				// Compara si los elementos son los mismos
				return oldItem.Nombre == newItem.Nombre
			}

			override fun areContentsTheSame(oldItem: Playa, newItem: Playa): Boolean {
				// Compara si los contenidos son iguales
				return oldItem == newItem
			}
		}
	}
}

