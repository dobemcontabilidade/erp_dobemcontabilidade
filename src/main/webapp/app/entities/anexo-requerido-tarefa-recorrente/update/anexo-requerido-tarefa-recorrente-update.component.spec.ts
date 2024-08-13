import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { AnexoRequeridoService } from 'app/entities/anexo-requerido/service/anexo-requerido.service';
import { ITarefaRecorrente } from 'app/entities/tarefa-recorrente/tarefa-recorrente.model';
import { TarefaRecorrenteService } from 'app/entities/tarefa-recorrente/service/tarefa-recorrente.service';
import { IAnexoRequeridoTarefaRecorrente } from '../anexo-requerido-tarefa-recorrente.model';
import { AnexoRequeridoTarefaRecorrenteService } from '../service/anexo-requerido-tarefa-recorrente.service';
import { AnexoRequeridoTarefaRecorrenteFormService } from './anexo-requerido-tarefa-recorrente-form.service';

import { AnexoRequeridoTarefaRecorrenteUpdateComponent } from './anexo-requerido-tarefa-recorrente-update.component';

describe('AnexoRequeridoTarefaRecorrente Management Update Component', () => {
  let comp: AnexoRequeridoTarefaRecorrenteUpdateComponent;
  let fixture: ComponentFixture<AnexoRequeridoTarefaRecorrenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anexoRequeridoTarefaRecorrenteFormService: AnexoRequeridoTarefaRecorrenteFormService;
  let anexoRequeridoTarefaRecorrenteService: AnexoRequeridoTarefaRecorrenteService;
  let anexoRequeridoService: AnexoRequeridoService;
  let tarefaRecorrenteService: TarefaRecorrenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoRequeridoTarefaRecorrenteUpdateComponent],
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
      .overrideTemplate(AnexoRequeridoTarefaRecorrenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnexoRequeridoTarefaRecorrenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anexoRequeridoTarefaRecorrenteFormService = TestBed.inject(AnexoRequeridoTarefaRecorrenteFormService);
    anexoRequeridoTarefaRecorrenteService = TestBed.inject(AnexoRequeridoTarefaRecorrenteService);
    anexoRequeridoService = TestBed.inject(AnexoRequeridoService);
    tarefaRecorrenteService = TestBed.inject(TarefaRecorrenteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AnexoRequerido query and add missing value', () => {
      const anexoRequeridoTarefaRecorrente: IAnexoRequeridoTarefaRecorrente = { id: 456 };
      const anexoRequerido: IAnexoRequerido = { id: 24761 };
      anexoRequeridoTarefaRecorrente.anexoRequerido = anexoRequerido;

      const anexoRequeridoCollection: IAnexoRequerido[] = [{ id: 12958 }];
      jest.spyOn(anexoRequeridoService, 'query').mockReturnValue(of(new HttpResponse({ body: anexoRequeridoCollection })));
      const additionalAnexoRequeridos = [anexoRequerido];
      const expectedCollection: IAnexoRequerido[] = [...additionalAnexoRequeridos, ...anexoRequeridoCollection];
      jest.spyOn(anexoRequeridoService, 'addAnexoRequeridoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoTarefaRecorrente });
      comp.ngOnInit();

      expect(anexoRequeridoService.query).toHaveBeenCalled();
      expect(anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing).toHaveBeenCalledWith(
        anexoRequeridoCollection,
        ...additionalAnexoRequeridos.map(expect.objectContaining),
      );
      expect(comp.anexoRequeridosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TarefaRecorrente query and add missing value', () => {
      const anexoRequeridoTarefaRecorrente: IAnexoRequeridoTarefaRecorrente = { id: 456 };
      const tarefaRecorrente: ITarefaRecorrente = { id: 7345 };
      anexoRequeridoTarefaRecorrente.tarefaRecorrente = tarefaRecorrente;

      const tarefaRecorrenteCollection: ITarefaRecorrente[] = [{ id: 1344 }];
      jest.spyOn(tarefaRecorrenteService, 'query').mockReturnValue(of(new HttpResponse({ body: tarefaRecorrenteCollection })));
      const additionalTarefaRecorrentes = [tarefaRecorrente];
      const expectedCollection: ITarefaRecorrente[] = [...additionalTarefaRecorrentes, ...tarefaRecorrenteCollection];
      jest.spyOn(tarefaRecorrenteService, 'addTarefaRecorrenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoTarefaRecorrente });
      comp.ngOnInit();

      expect(tarefaRecorrenteService.query).toHaveBeenCalled();
      expect(tarefaRecorrenteService.addTarefaRecorrenteToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaRecorrenteCollection,
        ...additionalTarefaRecorrentes.map(expect.objectContaining),
      );
      expect(comp.tarefaRecorrentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anexoRequeridoTarefaRecorrente: IAnexoRequeridoTarefaRecorrente = { id: 456 };
      const anexoRequerido: IAnexoRequerido = { id: 19375 };
      anexoRequeridoTarefaRecorrente.anexoRequerido = anexoRequerido;
      const tarefaRecorrente: ITarefaRecorrente = { id: 27046 };
      anexoRequeridoTarefaRecorrente.tarefaRecorrente = tarefaRecorrente;

      activatedRoute.data = of({ anexoRequeridoTarefaRecorrente });
      comp.ngOnInit();

      expect(comp.anexoRequeridosSharedCollection).toContain(anexoRequerido);
      expect(comp.tarefaRecorrentesSharedCollection).toContain(tarefaRecorrente);
      expect(comp.anexoRequeridoTarefaRecorrente).toEqual(anexoRequeridoTarefaRecorrente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoTarefaRecorrente>>();
      const anexoRequeridoTarefaRecorrente = { id: 123 };
      jest
        .spyOn(anexoRequeridoTarefaRecorrenteFormService, 'getAnexoRequeridoTarefaRecorrente')
        .mockReturnValue(anexoRequeridoTarefaRecorrente);
      jest.spyOn(anexoRequeridoTarefaRecorrenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoTarefaRecorrente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoRequeridoTarefaRecorrente }));
      saveSubject.complete();

      // THEN
      expect(anexoRequeridoTarefaRecorrenteFormService.getAnexoRequeridoTarefaRecorrente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anexoRequeridoTarefaRecorrenteService.update).toHaveBeenCalledWith(expect.objectContaining(anexoRequeridoTarefaRecorrente));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoTarefaRecorrente>>();
      const anexoRequeridoTarefaRecorrente = { id: 123 };
      jest.spyOn(anexoRequeridoTarefaRecorrenteFormService, 'getAnexoRequeridoTarefaRecorrente').mockReturnValue({ id: null });
      jest.spyOn(anexoRequeridoTarefaRecorrenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoTarefaRecorrente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoRequeridoTarefaRecorrente }));
      saveSubject.complete();

      // THEN
      expect(anexoRequeridoTarefaRecorrenteFormService.getAnexoRequeridoTarefaRecorrente).toHaveBeenCalled();
      expect(anexoRequeridoTarefaRecorrenteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoTarefaRecorrente>>();
      const anexoRequeridoTarefaRecorrente = { id: 123 };
      jest.spyOn(anexoRequeridoTarefaRecorrenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoTarefaRecorrente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anexoRequeridoTarefaRecorrenteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAnexoRequerido', () => {
      it('Should forward to anexoRequeridoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(anexoRequeridoService, 'compareAnexoRequerido');
        comp.compareAnexoRequerido(entity, entity2);
        expect(anexoRequeridoService.compareAnexoRequerido).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTarefaRecorrente', () => {
      it('Should forward to tarefaRecorrenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tarefaRecorrenteService, 'compareTarefaRecorrente');
        comp.compareTarefaRecorrente(entity, entity2);
        expect(tarefaRecorrenteService.compareTarefaRecorrente).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
