jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { CriterioAvaliacaoService } from '../service/criterio-avaliacao.service';

import { CriterioAvaliacaoDeleteDialogComponent } from './criterio-avaliacao-delete-dialog.component';

describe('CriterioAvaliacao Management Delete Component', () => {
  let comp: CriterioAvaliacaoDeleteDialogComponent;
  let fixture: ComponentFixture<CriterioAvaliacaoDeleteDialogComponent>;
  let service: CriterioAvaliacaoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CriterioAvaliacaoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(CriterioAvaliacaoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CriterioAvaliacaoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CriterioAvaliacaoService);
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
