using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Kolos2_s16748.Models
{
    public class BazaDanych : DbContext
    {
        public BazaDanych( DbContextOptions opt)
            :base(opt)
        {
        }
        public DbSet<Rent> Rents { get; set; }
        public DbSet<Car> Cars { get; set; }
    }
}
