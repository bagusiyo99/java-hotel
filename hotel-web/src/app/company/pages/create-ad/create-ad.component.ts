import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { CompanyService } from '../../services/company.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-ad',
  templateUrl: './create-ad.component.html',
  styleUrls: ['./create-ad.component.scss']
})
export class CreateAdComponent {
  validateForm!:FormGroup;

  selectedFile: File | null;
  imagePreview: string | ArrayBuffer | null;

    
  constructor (
    private companyService: CompanyService,
     private fb :FormBuilder,
     private notification :NzNotificationService,
     private router: Router
     ){}


  ngOnInit() {
    this.validateForm = this.fb.group({
      serviceName: [null, [Validators.required]],
      description: [null, [Validators.required]],
      price: [null, [Validators.required]],
    });
  }

  onFileSelected(event:any) {
    this.selectedFile = event.target.files[0];
    this.previewImage();
  }

  previewImage(){
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    }
    reader.readAsDataURL(this.selectedFile);
  }

  //01.51
  postAd(){
    const formData: FormData = new FormData();

    formData.append('img', this.selectedFile);
    formData.append('serviceName', this.validateForm.get('serviceName').value);
    formData.append('description', this.validateForm.get('description').value);
    formData.append('price', this.validateForm.get('price').value);

    this.companyService.postAd(formData).subscribe(res => {
      this.notification.success(
        'SUCCESS',
        ' Tambahkan Data Berhasil',
        {nzDuration : 5000}
      );
      this.router.navigateByUrl('/company/ads');
    },eror =>{
  this.notification.error(
        'ERROR',
        '${eror.eror}',
        {nzDuration : 5000}
      );
  })


  }

  
}
