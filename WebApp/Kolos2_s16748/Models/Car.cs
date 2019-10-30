using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Kolos2_s16748.Models
{
    public class Car
    {
        [Key]
        public int IdCar { get; set; }
        [Required(ErrorMessage ="Pole jest wymagane"),MaxLength(150,ErrorMessage ="Maksymalna długość 150 znaków")]
        public string RegisterNumber { get; set; }
        [Required(ErrorMessage = "Pole jest wymagane"), MaxLength(150, ErrorMessage = "Maksymalna długość 150 znaków")]
        public string Model { get; set; }
        public ICollection<Rent> Rents { get; set; }
    }
}
