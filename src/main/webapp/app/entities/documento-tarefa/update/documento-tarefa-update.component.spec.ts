import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITarefa } from 'app/entities/tarefa/tarefa.model';
import { TarefaService } from 'app/entities/tarefa/service/tarefa.service';
import { DocumentoTarefaService } from '../service/documento-tarefa.service';
import { IDocumentoTarefa } from '../documento-tarefa.model';
import { DocumentoTarefaFormService } from './documento-tarefa-form.service';

import { DocumentoTarefaUpdateComponent } from './documento-tarefa-update.component';

describe('DocumentoTarefa Management Update Component', () => {
  let comp: DocumentoTarefaUpdateComponent;
  let fixture: ComponentFixture<DocumentoTarefaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documentoTarefaFormService: DocumentoTarefaFormService;
  let documentoTarefaService: DocumentoTarefaService;
  let tarefaService: TarefaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DocumentoTarefaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DocumentoTarefaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentoTarefaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documentoTarefaFormService = TestBed.inject(DocumentoTarefaFormService);
    documentoTarefaService = TestBed.inject(DocumentoTarefaService);
    tarefaService = TestBed.inject(TarefaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Tarefa query and add missing value', () => {
      const documentoTarefa: IDocumentoTarefa = { id: 456 };
      const tarefa: ITarefa = { id: 4547 };
      documentoTarefa.tarefa = tarefa;

      const tarefaCollection: ITarefa[] = [{ id: 14805 }];
      jest.spyOn(tarefaService, 'query').mockReturnValue(of(new HttpResponse({ body: tarefaCollection })));
      const additionalTarefas = [tarefa];
      const expectedCollection: ITarefa[] = [...additionalTarefas, ...tarefaCollection];
      jest.spyOn(tarefaService, 'addTarefaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documentoTarefa });
      comp.ngOnInit();

      expect(tarefaService.query).toHaveBeenCalled();
      expect(tarefaService.addTarefaToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaCollection,
        ...additionalTarefas.map(expect.objectContaining),
      );
      expect(comp.tarefasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const documentoTarefa: IDocumentoTarefa = { id: 456 };
      const tarefa: ITarefa = { id: 30982 };
      documentoTarefa.tarefa = tarefa;

      activatedRoute.data = of({ documentoTarefa });
      comp.ngOnInit();

      expect(comp.tarefasSharedCollection).toContain(tarefa);
      expect(comp.documentoTarefa).toEqual(documentoTarefa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentoTarefa>>();
      const documentoTarefa = { id: 123 };
      jest.spyOn(documentoTarefaFormService, 'getDocumentoTarefa').mockReturnValue(documentoTarefa);
      jest.spyOn(documentoTarefaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentoTarefa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentoTarefa }));
      saveSubject.complete();

      // THEN
      expect(documentoTarefaFormService.getDocumentoTarefa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(documentoTarefaService.update).toHaveBeenCalledWith(expect.objectContaining(documentoTarefa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentoTarefa>>();
      const documentoTarefa = { id: 123 };
      jest.spyOn(documentoTarefaFormService, 'getDocumentoTarefa').mockReturnValue({ id: null });
      jest.spyOn(documentoTarefaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentoTarefa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentoTarefa }));
      saveSubject.complete();

      // THEN
      expect(documentoTarefaFormService.getDocumentoTarefa).toHaveBeenCalled();
      expect(documentoTarefaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentoTarefa>>();
      const documentoTarefa = { id: 123 };
      jest.spyOn(documentoTarefaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentoTarefa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documentoTarefaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTarefa', () => {
      it('Should forward to tarefaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tarefaService, 'compareTarefa');
        comp.compareTarefa(entity, entity2);
        expect(tarefaService.compareTarefa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
