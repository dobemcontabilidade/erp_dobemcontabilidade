import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { EstrangeiroService } from '../service/estrangeiro.service';
import { IEstrangeiro } from '../estrangeiro.model';
import { EstrangeiroFormService } from './estrangeiro-form.service';

import { EstrangeiroUpdateComponent } from './estrangeiro-update.component';

describe('Estrangeiro Management Update Component', () => {
  let comp: EstrangeiroUpdateComponent;
  let fixture: ComponentFixture<EstrangeiroUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let estrangeiroFormService: EstrangeiroFormService;
  let estrangeiroService: EstrangeiroService;
  let funcionarioService: FuncionarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EstrangeiroUpdateComponent],
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
      .overrideTemplate(EstrangeiroUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EstrangeiroUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    estrangeiroFormService = TestBed.inject(EstrangeiroFormService);
    estrangeiroService = TestBed.inject(EstrangeiroService);
    funcionarioService = TestBed.inject(FuncionarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Funcionario query and add missing value', () => {
      const estrangeiro: IEstrangeiro = { id: 456 };
      const funcionario: IFuncionario = { id: 27236 };
      estrangeiro.funcionario = funcionario;

      const funcionarioCollection: IFuncionario[] = [{ id: 29313 }];
      jest.spyOn(funcionarioService, 'query').mockReturnValue(of(new HttpResponse({ body: funcionarioCollection })));
      const additionalFuncionarios = [funcionario];
      const expectedCollection: IFuncionario[] = [...additionalFuncionarios, ...funcionarioCollection];
      jest.spyOn(funcionarioService, 'addFuncionarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ estrangeiro });
      comp.ngOnInit();

      expect(funcionarioService.query).toHaveBeenCalled();
      expect(funcionarioService.addFuncionarioToCollectionIfMissing).toHaveBeenCalledWith(
        funcionarioCollection,
        ...additionalFuncionarios.map(expect.objectContaining),
      );
      expect(comp.funcionariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const estrangeiro: IEstrangeiro = { id: 456 };
      const funcionario: IFuncionario = { id: 30211 };
      estrangeiro.funcionario = funcionario;

      activatedRoute.data = of({ estrangeiro });
      comp.ngOnInit();

      expect(comp.funcionariosSharedCollection).toContain(funcionario);
      expect(comp.estrangeiro).toEqual(estrangeiro);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstrangeiro>>();
      const estrangeiro = { id: 123 };
      jest.spyOn(estrangeiroFormService, 'getEstrangeiro').mockReturnValue(estrangeiro);
      jest.spyOn(estrangeiroService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estrangeiro });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estrangeiro }));
      saveSubject.complete();

      // THEN
      expect(estrangeiroFormService.getEstrangeiro).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(estrangeiroService.update).toHaveBeenCalledWith(expect.objectContaining(estrangeiro));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstrangeiro>>();
      const estrangeiro = { id: 123 };
      jest.spyOn(estrangeiroFormService, 'getEstrangeiro').mockReturnValue({ id: null });
      jest.spyOn(estrangeiroService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estrangeiro: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estrangeiro }));
      saveSubject.complete();

      // THEN
      expect(estrangeiroFormService.getEstrangeiro).toHaveBeenCalled();
      expect(estrangeiroService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstrangeiro>>();
      const estrangeiro = { id: 123 };
      jest.spyOn(estrangeiroService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estrangeiro });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(estrangeiroService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFuncionario', () => {
      it('Should forward to funcionarioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(funcionarioService, 'compareFuncionario');
        comp.compareFuncionario(entity, entity2);
        expect(funcionarioService.compareFuncionario).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
