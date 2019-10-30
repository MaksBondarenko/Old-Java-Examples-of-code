using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace Kolos2_s16748.Models
{
    public class Rent
    {
        [Key]
        public int IdRent { get; set; }
        [Required(ErrorMessage = "Pole jest wymagane"), MaxLength(150, ErrorMessage = "Maksymalna długość 255 znaków"),Display(Name="Opis")]
        public string Description { get; set; }
        [Required(ErrorMessage = "Pole jest wymagane"), MaxLength(150, ErrorMessage = "Maksymalna długość 255 znaków"), Display(Name = "Klient")]
        public string Client { get; set; }
        [Display(Name = "Z")]
        public DateTime DateFrom { get; set;}
        [Display(Name = "Do")]
        public DateTime DateTo { get; set; }
        [Required]
        public int IdCar { get; set; }
        [ForeignKey("IdCar"),Display(Name = "Samochod")]
        public Car Car { get; set; }
    }
}
