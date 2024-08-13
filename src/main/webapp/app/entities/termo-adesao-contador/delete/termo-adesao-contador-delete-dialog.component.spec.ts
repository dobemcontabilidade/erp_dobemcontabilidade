jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TermoAdesaoContadorService } from '../service/termo-adesao-contador.service';

import { TermoAdesaoContadorDeleteDialogComponent } from './termo-adesao-contador-delete-dialog.component';

describe('TermoAdesaoContador Management Delete Component', () => {
  let comp: TermoAdesaoContadorDeleteDialogComponent;
  let fixture: ComponentFixture<TermoAdesaoContadorDeleteDialogComponent>;
  let service: TermoAdesaoContadorService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TermoAdesaoContadorDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(TermoAdesaoContadorDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TermoAdesaoContadorDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TermoAdesaoContadorService);
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
