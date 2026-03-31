package project.master_detailflowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // ✅ Cleaned up Item List with Sub-Items and Icons
        val itemList = listOf(
            Item("Laptop", "High-performance laptop with 16GB RAM and 512GB SSD", 
                listOf("Dell XPS 13", "MacBook Air M2", "HP Spectre x360", "Lenovo ThinkPad X1"),
                android.R.drawable.ic_menu_slideshow),
            Item("Smartphone", "Android phone with 6.5-inch display and 5000mAh battery",
                listOf("Samsung Galaxy S23", "Google Pixel 7", "OnePlus 11", "Nothing Phone (2)"),
                android.R.drawable.ic_menu_call),
            Item("Headphones", "Wireless Bluetooth headphones with noise cancellation",
                listOf("Sony WH-1000XM5", "Bose QuietComfort 45", "Sennheiser Momentum 4", "AirPods Max"),
                android.R.drawable.ic_lock_silent_mode_off),
            Item("Smart Watch", "Fitness tracking smartwatch with heart rate monitor",
                listOf("Apple Watch Series 9", "Samsung Galaxy Watch 6", "Garmin Venu 3", "Fitbit Sense 2"),
                android.R.drawable.ic_menu_recent_history),
            Item("Tablet", "10-inch tablet suitable for study and entertainment",
                listOf("iPad Air", "Samsung Galaxy Tab S9", "Microsoft Surface Go 3", "Lenovo Tab P11"),
                android.R.drawable.ic_menu_gallery),
            Item("Camera", "DSLR camera with 24MP resolution and HD video recording",
                listOf("Canon EOS R6", "Sony A7 IV", "Nikon Z6 II", "Fujifilm X-T5"),
                android.R.drawable.ic_menu_camera),
            Item("Keyboard", "Mechanical keyboard with RGB lighting",
                listOf("Logitech G915", "Razer BlackWidow", "Keychron K2", "Corsair K70"),
                android.R.drawable.ic_menu_edit),
            Item("Mouse", "Wireless mouse with ergonomic design",
                listOf("Logitech MX Master 3S", "Razer DeathAdder", "SteelSeries Rival 3", "Microsoft Bluetooth Mouse"),
                android.R.drawable.ic_menu_mylocation),
            Item("Speaker", "Portable Bluetooth speaker with deep bass",
                listOf("JBL Flip 6", "Ultimate Ears Boom 3", "Bose SoundLink", "Sony SRS-XB33"),
                android.R.drawable.ic_lock_silent_mode),
            Item("Power Bank", "10000mAh fast charging power bank",
                listOf("Anker 737", "Samsung 25W Battery Pack", "Belkin Boost Charge", "Xiaomi Mi Power Bank"),
                android.R.drawable.ic_menu_save),
            Item("Monitor", "24-inch Full HD monitor for office and gaming",
                listOf("Dell UltraSharp", "ASUS TUF Gaming", "LG UltraGear", "Samsung Odyssey"),
                android.R.drawable.ic_menu_view),
            Item("Printer", "All-in-one printer with scanning and copying features",
                listOf("HP LaserJet", "Epson EcoTank", "Canon PIXMA", "Brother MFC"),
                android.R.drawable.ic_menu_share),
            Item("Router", "WiFi router with high-speed internet support",
                listOf("TP-Link Archer", "Netgear Nighthawk", "ASUS RT-AX88U", "Google Nest WiFi"),
                android.R.drawable.ic_menu_set_as),
            Item("Hard Disk", "1TB external hard drive for data storage",
                listOf("WD My Passport", "Seagate Expansion", "Samsung T7 SSD", "LaCie Rugged"),
                android.R.drawable.ic_menu_save),
            Item("USB Drive", "64GB USB flash drive for quick file transfer",
                listOf("SanDisk Ultra", "Kingston DataTraveler", "Samsung Bar Plus", "Lexar JumpDrive"),
                android.R.drawable.ic_menu_save),
            Item("RRR", "A historical action drama directed by S. S. Rajamouli",
                emptyList(), android.R.drawable.ic_menu_slideshow),
            Item("Baahubali", "Epic story of a warrior and kingdom",
                emptyList(), android.R.drawable.ic_menu_gallery),
            Item("KGF", "Story of rise of a powerful gangster",
                emptyList(), android.R.drawable.ic_menu_view),
            Item("Pushpa", "A red sandalwood smuggler's journey",
                emptyList(), android.R.drawable.ic_menu_share)
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemAdapter(itemList)
    }
}