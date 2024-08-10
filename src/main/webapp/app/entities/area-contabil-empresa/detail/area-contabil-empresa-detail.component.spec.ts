import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AreaContabilEmpresaDetailComponent } from './area-contabil-empresa-detail.component';

describe('AreaContabilEmpresa Management Detail Component', () => {
  let comp: AreaContabilEmpresaDetailComponent;
  let fixture: ComponentFixture<AreaContabilEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AreaContabilEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AreaContabilEmpresaDetailComponent,
              resolve: { areaContabilEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AreaContabilEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AreaContabilEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load areaContabilEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AreaContabilEmpresaDetailComponent);

      // THEN
      expect(instance.areaContabilEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
