jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TermoDeAdesaoService } from '../service/termo-de-adesao.service';

import { TermoDeAdesaoDeleteDialogComponent } from './termo-de-adesao-delete-dialog.component';

describe('TermoDeAdesao Management Delete Component', () => {
  let comp: TermoDeAdesaoDeleteDialogComponent;
  let fixture: ComponentFixture<TermoDeAdesaoDeleteDialogComponent>;
  let service: TermoDeAdesaoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TermoDeAdesaoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(TermoDeAdesaoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TermoDeAdesaoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TermoDeAdesaoService);
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
