jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ContadorResponsavelTarefaRecorrenteService } from '../service/contador-responsavel-tarefa-recorrente.service';

import { ContadorResponsavelTarefaRecorrenteDeleteDialogComponent } from './contador-responsavel-tarefa-recorrente-delete-dialog.component';

describe('ContadorResponsavelTarefaRecorrente Management Delete Component', () => {
  let comp: ContadorResponsavelTarefaRecorrenteDeleteDialogComponent;
  let fixture: ComponentFixture<ContadorResponsavelTarefaRecorrenteDeleteDialogComponent>;
  let service: ContadorResponsavelTarefaRecorrenteService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContadorResponsavelTarefaRecorrenteDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(ContadorResponsavelTarefaRecorrenteDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ContadorResponsavelTarefaRecorrenteDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ContadorResponsavelTarefaRecorrenteService);
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
