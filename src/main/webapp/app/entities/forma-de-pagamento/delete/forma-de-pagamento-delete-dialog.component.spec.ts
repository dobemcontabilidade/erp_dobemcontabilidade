jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FormaDePagamentoService } from '../service/forma-de-pagamento.service';

import { FormaDePagamentoDeleteDialogComponent } from './forma-de-pagamento-delete-dialog.component';

describe('FormaDePagamento Management Delete Component', () => {
  let comp: FormaDePagamentoDeleteDialogComponent;
  let fixture: ComponentFixture<FormaDePagamentoDeleteDialogComponent>;
  let service: FormaDePagamentoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FormaDePagamentoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(FormaDePagamentoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FormaDePagamentoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FormaDePagamentoService);
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
