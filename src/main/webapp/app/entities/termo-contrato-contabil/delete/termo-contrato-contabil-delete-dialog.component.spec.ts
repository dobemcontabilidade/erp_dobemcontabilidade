jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TermoContratoContabilService } from '../service/termo-contrato-contabil.service';

import { TermoContratoContabilDeleteDialogComponent } from './termo-contrato-contabil-delete-dialog.component';

describe('TermoContratoContabil Management Delete Component', () => {
  let comp: TermoContratoContabilDeleteDialogComponent;
  let fixture: ComponentFixture<TermoContratoContabilDeleteDialogComponent>;
  let service: TermoContratoContabilService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TermoContratoContabilDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(TermoContratoContabilDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TermoContratoContabilDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TermoContratoContabilService);
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
