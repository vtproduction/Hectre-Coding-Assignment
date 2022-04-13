package com.midsummer.orchardjob.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.midsummer.orchardjob.Constants
import com.midsummer.orchardjob.pojo.FieldConfig
import com.midsummer.orchardjob.pojo.OrchardJob
import com.midsummer.orchardjob.pojo.Staff
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.*
import org.junit.runner.RunWith
/**
 * Created by nienle on 13,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */




@RunWith(AndroidJUnit4::class)
class OrchardJobDatabaseTest : TestCase() {

    private lateinit var db : OrchardJobDatabase
    private lateinit var dao : OrchardJobDAO

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, OrchardJobDatabase::class.java).build()
        dao = db.orchardJobDAO()
    }

    @After
    fun closeDb() {
        db.close()
    }


    @Test
    fun testWriteRead() = runBlocking {

        val fieldConfig = FieldConfig(1, 1000)
        dao.insertFieldConfig(arrayListOf(fieldConfig))

        val configs = dao.getAllFieldConfig()
        assertTrue(configs.contains(fieldConfig))

    }


    @Test
    fun testModified() = runBlocking {
        val list: MutableList<OrchardJob> = arrayListOf()
        for (i in 1..100){
            list.add(
                OrchardJob(i,1, "orchard $i", "block: $i",
                            Staff(i, "name $i", "lastName $i"), arrayListOf())
            )
        }
        dao.insert(list)
        list.forEach { job ->
            job.staff = Staff(1, "first name modified", "last name modified")
        }
        dao.insert(list)
        val result = dao.getAllJobs()
        assertTrue(result.filter { r -> r.staff.firstName == "first name modified" }.size == 100)
    }

    @Test
    fun testType() = runBlocking{
        val list1: MutableList<OrchardJob> = arrayListOf()
        val list2: MutableList<OrchardJob> = arrayListOf()
        for (i in 1..50){
            list1.add(
                OrchardJob(i,Constants.JOB_TYPE_PRUNING, "orchard $i", "block: $i",
                    Staff(i, "name $i", "lastName $i"), arrayListOf())
            )
        }
        for (i in 1..50){
            list2.add(
                OrchardJob(i,Constants.JOB_TYPE_THINNING, "orchard $i", "block: $i",
                    Staff(i, "name $i", "lastName $i"), arrayListOf())
            )
        }

        dao.insert(list1)
        dao.insert(list2)

        val r1 = dao.getPruningJobs()
        val r2 = dao.getThinningJobs()
        val r = dao.getAllJobs()
        assertTrue(r1.size + r2.size == r.size)
    }


}