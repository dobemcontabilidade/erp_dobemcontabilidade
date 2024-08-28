jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AssinaturaEmpresaService } from '../service/assinatura-empresa.service';

import { AssinaturaEmpresaDeleteDialogComponent } from './assinatura-empresa-delete-dialog.component';

describe('AssinaturaEmpresa Management Delete Component', () => {
  let comp: AssinaturaEmpresaDeleteDialogComponent;
  let fixture: ComponentFixture<AssinaturaEmpresaDeleteDialogComponent>;
  let service: AssinaturaEmpresaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AssinaturaEmpresaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AssinaturaEmpresaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AssinaturaEmpresaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AssinaturaEmpresaService);
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
