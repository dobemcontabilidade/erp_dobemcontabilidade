jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AgendaTarefaRecorrenteExecucaoService } from '../service/agenda-tarefa-recorrente-execucao.service';

import { AgendaTarefaRecorrenteExecucaoDeleteDialogComponent } from './agenda-tarefa-recorrente-execucao-delete-dialog.component';

describe('AgendaTarefaRecorrenteExecucao Management Delete Component', () => {
  let comp: AgendaTarefaRecorrenteExecucaoDeleteDialogComponent;
  let fixture: ComponentFixture<AgendaTarefaRecorrenteExecucaoDeleteDialogComponent>;
  let service: AgendaTarefaRecorrenteExecucaoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AgendaTarefaRecorrenteExecucaoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AgendaTarefaRecorrenteExecucaoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AgendaTarefaRecorrenteExecucaoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AgendaTarefaRecorrenteExecucaoService);
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
