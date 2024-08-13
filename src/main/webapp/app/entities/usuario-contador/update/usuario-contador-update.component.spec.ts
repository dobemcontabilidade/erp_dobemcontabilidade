import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { UsuarioContadorService } from '../service/usuario-contador.service';
import { IUsuarioContador } from '../usuario-contador.model';
import { UsuarioContadorFormService } from './usuario-contador-form.service';

import { UsuarioContadorUpdateComponent } from './usuario-contador-update.component';

describe('UsuarioContador Management Update Component', () => {
  let comp: UsuarioContadorUpdateComponent;
  let fixture: ComponentFixture<UsuarioContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let usuarioContadorFormService: UsuarioContadorFormService;
  let usuarioContadorService: UsuarioContadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [UsuarioContadorUpdateComponent],
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
      .overrideTemplate(UsuarioContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UsuarioContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    usuarioContadorFormService = TestBed.inject(UsuarioContadorFormService);
    usuarioContadorService = TestBed.inject(UsuarioContadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const usuarioContador: IUsuarioContador = { id: 456 };

      activatedRoute.data = of({ usuarioContador });
      comp.ngOnInit();

      expect(comp.usuarioContador).toEqual(usuarioContador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioContador>>();
      const usuarioContador = { id: 123 };
      jest.spyOn(usuarioContadorFormService, 'getUsuarioContador').mockReturnValue(usuarioContador);
      jest.spyOn(usuarioContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuarioContador }));
      saveSubject.complete();

      // THEN
      expect(usuarioContadorFormService.getUsuarioContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(usuarioContadorService.update).toHaveBeenCalledWith(expect.objectContaining(usuarioContador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioContador>>();
      const usuarioContador = { id: 123 };
      jest.spyOn(usuarioContadorFormService, 'getUsuarioContador').mockReturnValue({ id: null });
      jest.spyOn(usuarioContadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioContador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuarioContador }));
      saveSubject.complete();

      // THEN
      expect(usuarioContadorFormService.getUsuarioContador).toHaveBeenCalled();
      expect(usuarioContadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuarioContador>>();
      const usuarioContador = { id: 123 };
      jest.spyOn(usuarioContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuarioContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(usuarioContadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
