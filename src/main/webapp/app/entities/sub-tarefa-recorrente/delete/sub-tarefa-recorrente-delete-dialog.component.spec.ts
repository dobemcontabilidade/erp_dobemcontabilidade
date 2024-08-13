jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SubTarefaRecorrenteService } from '../service/sub-tarefa-recorrente.service';

import { SubTarefaRecorrenteDeleteDialogComponent } from './sub-tarefa-recorrente-delete-dialog.component';

describe('SubTarefaRecorrente Management Delete Component', () => {
  let comp: SubTarefaRecorrenteDeleteDialogComponent;
  let fixture: ComponentFixture<SubTarefaRecorrenteDeleteDialogComponent>;
  let service: SubTarefaRecorrenteService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SubTarefaRecorrenteDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(SubTarefaRecorrenteDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SubTarefaRecorrenteDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SubTarefaRecorrenteService);
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
