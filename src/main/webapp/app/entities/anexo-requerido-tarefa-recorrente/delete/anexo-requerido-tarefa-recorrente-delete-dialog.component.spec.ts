jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AnexoRequeridoTarefaRecorrenteService } from '../service/anexo-requerido-tarefa-recorrente.service';

import { AnexoRequeridoTarefaRecorrenteDeleteDialogComponent } from './anexo-requerido-tarefa-recorrente-delete-dialog.component';

describe('AnexoRequeridoTarefaRecorrente Management Delete Component', () => {
  let comp: AnexoRequeridoTarefaRecorrenteDeleteDialogComponent;
  let fixture: ComponentFixture<AnexoRequeridoTarefaRecorrenteDeleteDialogComponent>;
  let service: AnexoRequeridoTarefaRecorrenteService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoRequeridoTarefaRecorrenteDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AnexoRequeridoTarefaRecorrenteDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnexoRequeridoTarefaRecorrenteDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnexoRequeridoTarefaRecorrenteService);
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
