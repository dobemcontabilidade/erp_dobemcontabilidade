jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FornecedorCertificadoService } from '../service/fornecedor-certificado.service';

import { FornecedorCertificadoDeleteDialogComponent } from './fornecedor-certificado-delete-dialog.component';

describe('FornecedorCertificado Management Delete Component', () => {
  let comp: FornecedorCertificadoDeleteDialogComponent;
  let fixture: ComponentFixture<FornecedorCertificadoDeleteDialogComponent>;
  let service: FornecedorCertificadoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FornecedorCertificadoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(FornecedorCertificadoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FornecedorCertificadoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FornecedorCertificadoService);
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
