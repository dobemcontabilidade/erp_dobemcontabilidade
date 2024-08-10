jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PrazoAssinaturaService } from '../service/prazo-assinatura.service';

import { PrazoAssinaturaDeleteDialogComponent } from './prazo-assinatura-delete-dialog.component';

describe('PrazoAssinatura Management Delete Component', () => {
  let comp: PrazoAssinaturaDeleteDialogComponent;
  let fixture: ComponentFixture<PrazoAssinaturaDeleteDialogComponent>;
  let service: PrazoAssinaturaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PrazoAssinaturaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(PrazoAssinaturaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PrazoAssinaturaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PrazoAssinaturaService);
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
