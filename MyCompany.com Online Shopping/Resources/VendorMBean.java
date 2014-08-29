
package edu.mum.cs.cs490.mbean;

import edu.mum.cs.cs490.mycomp.entity.Vendor;
import edu.mum.cs.cs490.mycomp.service.VendorService;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author TalakB
 */
@Named
@SessionScoped
public class VendorMBean implements Serializable {
    @EJB 
    VendorService vendorService;    
    Vendor vendor = new Vendor();

    public VendorMBean() {
    }

    public VendorService getVendorService() {
        return vendorService;
    }

    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
    
      public String addVendor() throws Exception {
       if (vendorService.saveVendor(vendor)) {
            return "registration_confirmation";
        }
        return null;
    }
     
}
