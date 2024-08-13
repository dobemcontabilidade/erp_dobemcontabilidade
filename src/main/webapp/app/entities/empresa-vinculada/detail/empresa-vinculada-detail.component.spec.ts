import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EmpresaVinculadaDetailComponent } from './empresa-vinculada-detail.component';

describe('EmpresaVinculada Management Detail Component', () => {
  let comp: EmpresaVinculadaDetailComponent;
  let fixture: ComponentFixture<EmpresaVinculadaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmpresaVinculadaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EmpresaVinculadaDetailComponent,
              resolve: { empresaVinculada: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EmpresaVinculadaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmpresaVinculadaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load empresaVinculada on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EmpresaVinculadaDetailComponent);

      // THEN
      expect(instance.empresaVinculada()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
