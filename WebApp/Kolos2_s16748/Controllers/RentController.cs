using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Kolos2_s16748.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace Kolos2_s16748.Controllers
{
    public class RentController : Controller
    {
        public readonly BazaDanych _context;
        public RentController(BazaDanych context)
        {
            _context = context;
        }
        public IActionResult Index()
        {
            ViewBag.Rents = _context.Rents.Include(p => p.Car)
                            .OrderBy(p => p.IdRent)
                            .ToList();
            ViewBag.Cars = _context.Cars.OrderBy(t => t.IdCar).ToList();
            return View();
        }
        public IActionResult Dodaj(Rent newRent)
        {
            if (!ModelState.IsValid|| newRent.DateFrom > newRent.DateTo)
            {
                ViewBag.Rents = _context.Rents.Include(p => p.Car)
                                .OrderBy(p => p.IdRent)
                                .ToList();
                ViewBag.Cars = _context.Cars.OrderBy(t => t.IdCar).ToList();
                return View("Index", newRent);
            }
            _context.Rents.Add(newRent);
            _context.SaveChanges();

            return RedirectToAction("Index");
        }
    }
}