import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IServicoContabilAssinaturaEmpresa } from 'app/entities/servico-contabil-assinatura-empresa/servico-contabil-assinatura-empresa.model';
import { ServicoContabilAssinaturaEmpresaService } from 'app/entities/servico-contabil-assinatura-empresa/service/servico-contabil-assinatura-empresa.service';
import { TarefaRecorrenteService } from '../service/tarefa-recorrente.service';
import { ITarefaRecorrente } from '../tarefa-recorrente.model';
import { TarefaRecorrenteFormService } from './tarefa-recorrente-form.service';

import { TarefaRecorrenteUpdateComponent } from './tarefa-recorrente-update.component';

describe('TarefaRecorrente Management Update Component', () => {
  let comp: TarefaRecorrenteUpdateComponent;
  let fixture: ComponentFixture<TarefaRecorrenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tarefaRecorrenteFormService: TarefaRecorrenteFormService;
  let tarefaRecorrenteService: TarefaRecorrenteService;
  let servicoContabilAssinaturaEmpresaService: ServicoContabilAssinaturaEmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TarefaRecorrenteUpdateComponent],
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
      .overrideTemplate(TarefaRecorrenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TarefaRecorrenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tarefaRecorrenteFormService = TestBed.inject(TarefaRecorrenteFormService);
    tarefaRecorrenteService = TestBed.inject(TarefaRecorrenteService);
    servicoContabilAssinaturaEmpresaService = TestBed.inject(ServicoContabilAssinaturaEmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ServicoContabilAssinaturaEmpresa query and add missing value', () => {
      const tarefaRecorrente: ITarefaRecorrente = { id: 456 };
      const servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa = { id: 25007 };
      tarefaRecorrente.servicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresa;

      const servicoContabilAssinaturaEmpresaCollection: IServicoContabilAssinaturaEmpresa[] = [{ id: 21947 }];
      jest
        .spyOn(servicoContabilAssinaturaEmpresaService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: servicoContabilAssinaturaEmpresaCollection })));
      const additionalServicoContabilAssinaturaEmpresas = [servicoContabilAssinaturaEmpresa];
      const expectedCollection: IServicoContabilAssinaturaEmpresa[] = [
        ...additionalServicoContabilAssinaturaEmpresas,
        ...servicoContabilAssinaturaEmpresaCollection,
      ];
      jest
        .spyOn(servicoContabilAssinaturaEmpresaService, 'addServicoContabilAssinaturaEmpresaToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefaRecorrente });
      comp.ngOnInit();

      expect(servicoContabilAssinaturaEmpresaService.query).toHaveBeenCalled();
      expect(servicoContabilAssinaturaEmpresaService.addServicoContabilAssinaturaEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        servicoContabilAssinaturaEmpresaCollection,
        ...additionalServicoContabilAssinaturaEmpresas.map(expect.objectContaining),
      );
      expect(comp.servicoContabilAssinaturaEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tarefaRecorrente: ITarefaRecorrente = { id: 456 };
      const servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa = { id: 27440 };
      tarefaRecorrente.servicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresa;

      activatedRoute.data = of({ tarefaRecorrente });
      comp.ngOnInit();

      expect(comp.servicoContabilAssinaturaEmpresasSharedCollection).toContain(servicoContabilAssinaturaEmpresa);
      expect(comp.tarefaRecorrente).toEqual(tarefaRecorrente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaRecorrente>>();
      const tarefaRecorrente = { id: 123 };
      jest.spyOn(tarefaRecorrenteFormService, 'getTarefaRecorrente').mockReturnValue(tarefaRecorrente);
      jest.spyOn(tarefaRecorrenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaRecorrente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaRecorrente }));
      saveSubject.complete();

      // THEN
      expect(tarefaRecorrenteFormService.getTarefaRecorrente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tarefaRecorrenteService.update).toHaveBeenCalledWith(expect.objectContaining(tarefaRecorrente));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaRecorrente>>();
      const tarefaRecorrente = { id: 123 };
      jest.spyOn(tarefaRecorrenteFormService, 'getTarefaRecorrente').mockReturnValue({ id: null });
      jest.spyOn(tarefaRecorrenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaRecorrente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaRecorrente }));
      saveSubject.complete();

      // THEN
      expect(tarefaRecorrenteFormService.getTarefaRecorrente).toHaveBeenCalled();
      expect(tarefaRecorrenteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaRecorrente>>();
      const tarefaRecorrente = { id: 123 };
      jest.spyOn(tarefaRecorrenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaRecorrente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tarefaRecorrenteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareServicoContabilAssinaturaEmpresa', () => {
      it('Should forward to servicoContabilAssinaturaEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(servicoContabilAssinaturaEmpresaService, 'compareServicoContabilAssinaturaEmpresa');
        comp.compareServicoContabilAssinaturaEmpresa(entity, entity2);
        expect(servicoContabilAssinaturaEmpresaService.compareServicoContabilAssinaturaEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
