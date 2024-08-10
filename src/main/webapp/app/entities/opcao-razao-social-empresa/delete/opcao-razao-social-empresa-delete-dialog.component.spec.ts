jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { OpcaoRazaoSocialEmpresaService } from '../service/opcao-razao-social-empresa.service';

import { OpcaoRazaoSocialEmpresaDeleteDialogComponent } from './opcao-razao-social-empresa-delete-dialog.component';

describe('OpcaoRazaoSocialEmpresa Management Delete Component', () => {
  let comp: OpcaoRazaoSocialEmpresaDeleteDialogComponent;
  let fixture: ComponentFixture<OpcaoRazaoSocialEmpresaDeleteDialogComponent>;
  let service: OpcaoRazaoSocialEmpresaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OpcaoRazaoSocialEmpresaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(OpcaoRazaoSocialEmpresaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OpcaoRazaoSocialEmpresaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(OpcaoRazaoSocialEmpresaService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
